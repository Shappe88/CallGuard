import { Router } from "express";
import { getVersion, getFullBlocklist, getDelta } from "../controllers/sync.controller";

const router = Router();

router.get("/version", getVersion);
router.get("/full", getFullBlocklist);
router.get("/delta", getDelta);

// Dummy endpoints for status and telemetry
router.post("/device/status", (req, res) => {
  console.log("Device status:", req.body);
  res.status(200).json({ ok: true });
});

router.post("/telemetry/audit", (req, res) => {
  console.log("Audit telemetry:", req.body);
  res.status(200).json({ ok: true });
});

export default router;
