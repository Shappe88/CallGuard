import { Request, Response } from "express";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { prisma, incrementGlobalVersion } from "../services/db.service";

const JWT_SECRET = process.env.JWT_SECRET || "supersecretkey";

export const login = async (req: Request, res: Response) => {
  const { username, password } = req.body;
  const admin = await prisma.admin.findUnique({ where: { username } });

  if (!admin) {
    return res.status(401).json({ error: "Invalid credentials" });
  }

  const isValid = await bcrypt.compare(password, admin.password);
  if (!isValid) {
    return res.status(401).json({ error: "Invalid credentials" });
  }

  const token = jwt.sign({ id: admin.id, username: admin.username }, JWT_SECRET, { expiresIn: "1d" });
  res.json({ token });
};


export const getNumbers = async (req: Request, res: Response) => {
  const numbers = await prisma.numberEntry.findMany({
    where: { active: true },
    orderBy: { createdAt: "desc" }
  });
  res.json(numbers);
};

export const addNumber = async (req: Request, res: Response) => {
  const { normalizedNumber, displayLabel, type, reasonCode, severity, category } = req.body;
  try {
    const globalState = await incrementGlobalVersion();
    const entry = await prisma.numberEntry.create({
      data: {
        normalizedNumber,
        displayLabel,
        type,
        reasonCode,
        severity,
        category,
        addedAtVersion: globalState.currentVersion,
        active: true,
      }
    });
    res.json(entry);
  } catch (error) {
    res.status(400).json({ error: "Failed to add number. Maybe it already exists." });
  }
};

export const updateNumber = async (req: Request, res: Response) => {
  const { id } = req.params;
  const { displayLabel, reasonCode, severity, category } = req.body;
  try {
    await incrementGlobalVersion();
    const entry = await prisma.numberEntry.update({
      where: { id: Number(id) },
      data: {
        displayLabel,
        reasonCode,
        severity,
        category,
      }
    });
    res.json(entry);
  } catch (error) {
    res.status(400).json({ error: "Failed to update number." });
  }
};

export const deleteNumber = async (req: Request, res: Response) => {
  const { id } = req.params;
  try {
    const globalState = await incrementGlobalVersion();
    const entry = await prisma.numberEntry.update({
      where: { id: Number(id) },
      data: {
        active: false,
        removedAtVersion: globalState.currentVersion,
      }
    });
    res.json({ message: "Deleted successfully", entry });
  } catch (error) {
    res.status(400).json({ error: "Failed to delete number." });
  }
};
