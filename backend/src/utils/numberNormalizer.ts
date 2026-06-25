export const normalizeNumber = (rawNumber: string | null | undefined, defaultCountryCode: string = "91"): string | null => {
  if (!rawNumber) return null;
  
  let digitsOnly = String(rawNumber).replace(/\D/g, "");
  
  while (digitsOnly.startsWith("0")) {
    digitsOnly = digitsOnly.substring(1);
  }

  if (digitsOnly.length === 0) return null;

  if (digitsOnly.length === 10) {
    return `+${defaultCountryCode}${digitsOnly}`;
  }
  
  if (digitsOnly.length === 10 + defaultCountryCode.length && digitsOnly.startsWith(defaultCountryCode)) {
    return `+${digitsOnly}`;
  }

  return `+${digitsOnly}`;
};
