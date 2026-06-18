// Generates an Ed25519 keypair for signing blocklist payloads.
//
// Usage: node generate_keys.js
//
// Output:
//   - private.pem  (KEEP SECRET — used by server.js to sign payloads)
//   - public.pem   (give the base64 SubjectPublicKeyInfo to the Android app)
//
// After running, copy the printed "PUBLIC KEY (base64 for Android)" value
// into SignatureVerifier.BACKEND_PUBLIC_KEY_B64 in the Android project.

const { generateKeyPairSync } = require('crypto');
const fs = require('fs');

const { publicKey, privateKey } = generateKeyPairSync('ed25519');

fs.writeFileSync('private.pem', privateKey.export({ type: 'pkcs8', format: 'pem' }));
fs.writeFileSync('public.pem', publicKey.export({ type: 'spki', format: 'pem' }));

const publicDer = publicKey.export({ type: 'spki', format: 'der' });
console.log('Keys written: private.pem (keep secret), public.pem');
console.log('');
console.log('PUBLIC KEY (base64 for Android SignatureVerifier.BACKEND_PUBLIC_KEY_B64):');
console.log(publicDer.toString('base64'));
