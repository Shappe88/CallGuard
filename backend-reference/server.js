// Minimal reference server implementing the CallGuard sync API.
//
// NOT PRODUCTION READY:
//  - No authentication on read endpoints (add for real deployment)
//  - In-memory data only (resets on restart)
//  - Admin write endpoints are unauthenticated examples only
//
// Run:
//   npm install
//   node generate_keys.js     (first time only — creates private.pem/public.pem)
//   node server.js
//
// Then point the Android app's NetworkModule.BASE_URL at this server
// (e.g. http://10.0.2.2:3000/ for the Android emulator) and set
// SignatureVerifier.BACKEND_PUBLIC_KEY_B64 to the printed public key,
// and DISABLE certificate pinning for local HTTP testing only.

const express = require('express');
const crypto = require('crypto');
const fs = require('fs');

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3000;

let privateKey;
try {
  privateKey = crypto.createPrivateKey(fs.readFileSync('private.pem'));
} catch (e) {
  console.error('private.pem not found. Run: node generate_keys.js');
  process.exit(1);
}

// ---- In-memory "current blocklist" state ----------------------------------

let currentVersion = 2;

const blockedNumbers = [
  {
    normalized_number: '+919999999999',
    display_label: 'Reported scam number',
    reason_code: 'FRAUD',
    severity: 'HIGH',
    source: 'BACKEND_TEST'
  }
];

const allowlistedNumbers = [
  {
    normalized_number: '+91100',
    display_label: 'Police',
    category: 'EMERGENCY',
    source: 'BACKEND_TEST'
  },
  {
    normalized_number: '+911800000000',
    display_label: 'IT/SOC Helpdesk',
    category: 'SOC',
    source: 'BACKEND_TEST'
  }
];

const policy = {
  kill_switch: 'false',
  max_offline_days: '14'
};

// ---- Helpers ----------------------------------------------------------------

function signPayload(obj) {
  const json = JSON.stringify(obj);
  const data = Buffer.from(json, 'utf8');
  const signature = crypto.sign(null, data, privateKey).toString('base64');
  const checksum = crypto.createHash('sha256').update(data).digest('hex');
  return { json, signature, checksum };
}

function buildFullPayload() {
  const base = {
    version: currentVersion,
    released_at: new Date().toISOString(),
    blocked_numbers: blockedNumbers,
    allowlisted_numbers: allowlistedNumbers,
    policy
  };
  // Sign over the object WITHOUT signature/checksum fields, then attach them.
  // NOTE: the Android client re-serializes the full response (including
  // signature/checksum fields) when computing its checksum/signature check
  // in this reference implementation — for production, define a canonical
  // signing format (e.g. sign only the "payload" sub-object) and keep both
  // sides in exact agreement. This reference keeps it simple by signing the
  // base object and the client must be updated to match if used seriously.
  const { signature, checksum } = signPayload(base);
  return { ...base, signature, checksum };
}

// ---- Endpoints ----------------------------------------------------------------

app.get('/api/v1/blocklist/version', (req, res) => {
  const data = Buffer.from(String(currentVersion), 'utf8');
  res.json({
    version: currentVersion,
    checksum: crypto.createHash('sha256').update(data).digest('hex'),
    signed_at: new Date().toISOString()
  });
});

app.get('/api/v1/blocklist/full', (req, res) => {
  res.json(buildFullPayload());
});

app.get('/api/v1/blocklist/delta', (req, res) => {
  const since = parseInt(req.query.since || '0', 10);
  const base = {
    from: since,
    to: currentVersion,
    added_blocked: since < currentVersion ? blockedNumbers : [],
    removed_blocked: [],
    added_allowlisted: since < currentVersion ? allowlistedNumbers : [],
    removed_allowlisted: [],
    policy
  };
  const { signature, checksum } = signPayload(base);
  res.json({ ...base, signature, checksum });
});

app.post('/api/v1/device/status', (req, res) => {
  console.log('Device status:', JSON.stringify(req.body));
  res.status(200).json({ ok: true });
});

app.post('/api/v1/telemetry/audit', (req, res) => {
  console.log('Audit telemetry:', JSON.stringify(req.body));
  res.status(200).json({ ok: true });
});

// ---- Simple admin endpoints (for testing OTA updates) --------------------------

app.post('/admin/bump-version', (req, res) => {
  currentVersion += 1;
  res.json({ version: currentVersion });
});

app.post('/admin/add-blocked', (req, res) => {
  blockedNumbers.push(req.body);
  currentVersion += 1;
  res.json({ version: currentVersion, blockedNumbers });
});

app.listen(PORT, () => {
  console.log(`CallGuard reference backend listening on port ${PORT}`);
  console.log(`Current blocklist version: ${currentVersion}`);
});
