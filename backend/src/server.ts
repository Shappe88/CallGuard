import app from "./app";
import { prisma } from "./services/db.service";
import bcrypt from "bcrypt";

const PORT = process.env.PORT || 3000;

async function seedAdmin() {
  const username = "admin";
  const password = "battle@xe1";

  const existing = await prisma.admin.findUnique({ where: { username } });
  if (!existing) {
    const passwordHash = await bcrypt.hash(password, 10);
    await prisma.admin.create({
      data: {
        username,
        password: passwordHash,
      }
    });
    console.log("Default admin created.");
  }
}

seedAdmin().then(() => {
  app.listen(PORT as number, "0.0.0.0", () => {
    console.log(`CallGuard Professional Backend listening on port ${PORT} at 0.0.0.0`);
  });
}).catch(console.error);
