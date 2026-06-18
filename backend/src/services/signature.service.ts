import crypto from "crypto";
import fs from "fs";
import path from "path";

let privateKey: crypto.KeyObject;

try {
  privateKey = crypto.createPrivateKey(
    fs.readFileSync(path.resolve(process.cwd(), "private.pem"))
  );
} catch (e) {
  console.error("private.pem not found. Run key generation script.");
}

export const signPayload = (obj: any) => {
  const json = JSON.stringify(obj);
  const data = Buffer.from(json, "utf8");
  const signature = crypto.sign(null, data, privateKey).toString("base64");
  const checksum = crypto.createHash("sha256").update(data).digest("hex");
  return { signature, checksum };
};
