import { Request, Response } from "express";
import { prisma, getGlobalState } from "../services/db.service";
import { signPayload } from "../services/signature.service";

const mapNumberEntry = (entry: any) => ({
  normalized_number: entry.normalizedNumber,
  display_label: entry.displayLabel,
  reason_code: entry.reasonCode,
  severity: entry.severity,
  category: entry.category,
  source: entry.source
});

export const getVersion = async (req: Request, res: Response) => {
  const state = await getGlobalState();
  const data = Buffer.from(String(state.currentVersion), "utf8");
  const crypto = require("crypto");
  res.json({
    version: state.currentVersion,
    checksum: crypto.createHash("sha256").update(data).digest("hex"),
    signed_at: new Date().toISOString()
  });
};

export const getFullBlocklist = async (req: Request, res: Response) => {
  const state = await getGlobalState();
  
  const allActive = await prisma.numberEntry.findMany({ where: { active: true } });
  const blockedNumbers = allActive.filter(n => n.type === "BLOCKED").map(mapNumberEntry);
  const allowlistedNumbers = allActive.filter(n => n.type === "ALLOWLISTED").map(mapNumberEntry);
  
  const policies = await prisma.policy.findMany();
  const policyObj: any = {};
  policies.forEach(p => policyObj[p.key] = p.value);
  if (Object.keys(policyObj).length === 0) {
    policyObj.kill_switch = "false";
    policyObj.max_offline_days = "14";
  }

  const base = {
    version: state.currentVersion,
    released_at: new Date().toISOString(),
    blocked_numbers: blockedNumbers,
    allowlisted_numbers: allowlistedNumbers,
    policy: policyObj
  };

  const { signature, checksum } = signPayload(base);
  res.json({ ...base, signature, checksum });
};

export const getDelta = async (req: Request, res: Response) => {
  const since = parseInt(req.query.since as string || "0", 10);
  const state = await getGlobalState();

  const added = await prisma.numberEntry.findMany({
    where: { active: true, addedAtVersion: { gt: since } }
  });
  const removed = await prisma.numberEntry.findMany({
    where: { active: false, removedAtVersion: { gt: since } }
  });

  const addedBlocked = added.filter(n => n.type === "BLOCKED").map(mapNumberEntry);
  const addedAllowlisted = added.filter(n => n.type === "ALLOWLISTED").map(mapNumberEntry);

  const removedBlocked = removed.filter(n => n.type === "BLOCKED").map(n => n.normalizedNumber);
  const removedAllowlisted = removed.filter(n => n.type === "ALLOWLISTED").map(n => n.normalizedNumber);

  const policies = await prisma.policy.findMany();
  const policyObj: any = {};
  policies.forEach(p => policyObj[p.key] = p.value);
  if (Object.keys(policyObj).length === 0) {
    policyObj.kill_switch = "false";
    policyObj.max_offline_days = "14";
  }

  const base = {
    from: since,
    to: state.currentVersion,
    added_blocked: addedBlocked,
    removed_blocked: removedBlocked,
    added_allowlisted: addedAllowlisted,
    removed_allowlisted: removedAllowlisted,
    policy: policyObj
  };

  const { signature, checksum } = signPayload(base);
  res.json({ ...base, signature, checksum });
};
