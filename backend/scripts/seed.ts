import { PrismaClient } from "@prisma/client";
import bcrypt from "bcrypt";

const prisma = new PrismaClient();

async function main() {
  console.log("Seeding database...");

  // Seed GlobalState
  await prisma.globalState.upsert({
    where: { id: 1 },
    update: {},
    create: {
      id: 1,
      currentVersion: 2,
    },
  });

  // Seed Admin
  const passwordHash = await bcrypt.hash("password123", 10);
  await prisma.admin.upsert({
    where: { username: "admin" },
    update: {},
    create: {
      username: "admin",
      password: passwordHash,
    },
  });

  // Seed Test Numbers
  const numbers = [
    {
      normalizedNumber: "+919999999999",
      displayLabel: "Reported scam number",
      type: "BLOCKED",
      reasonCode: "FRAUD",
      severity: "HIGH",
      source: "BACKEND_TEST",
      addedAtVersion: 1,
    },
    {
      normalizedNumber: "+91100",
      displayLabel: "Police",
      type: "ALLOWLISTED",
      category: "EMERGENCY",
      source: "BACKEND_TEST",
      addedAtVersion: 1,
    },
    {
      normalizedNumber: "+911800000000",
      displayLabel: "IT/SOC Helpdesk",
      type: "ALLOWLISTED",
      category: "SOC",
      source: "BACKEND_TEST",
      addedAtVersion: 1,
    },
  ];

  for (const num of numbers) {
    await prisma.numberEntry.upsert({
      where: { normalizedNumber: num.normalizedNumber },
      update: {},
      create: num,
    });
  }

  // Seed Policies
  await prisma.policy.upsert({
    where: { key: "kill_switch" },
    update: {},
    create: { key: "kill_switch", value: "false" },
  });

  await prisma.policy.upsert({
    where: { key: "max_offline_days" },
    update: {},
    create: { key: "max_offline_days", value: "14" },
  });

  console.log("Database seeding completed.");
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
