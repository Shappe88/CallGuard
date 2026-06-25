import { Request, Response } from "express";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { prisma, incrementGlobalVersion } from "../services/db.service";
import fs from "fs";
import csvParser from "csv-parser";
import * as xlsx from "xlsx";
import { normalizeNumber } from "../utils/numberNormalizer";

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
  const normalized = normalizeNumber(normalizedNumber);
  if (!normalized) {
    return res.status(400).json({ error: "Invalid phone number format." });
  }

  try {
    const globalState = await incrementGlobalVersion();
    const entry = await prisma.numberEntry.create({
      data: {
        normalizedNumber: normalized,
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

export const bulkUpload = async (req: Request, res: Response) => {
  if (!req.file) {
    return res.status(400).json({ error: "No file uploaded" });
  }

  const filePath = req.file.path;
  const numbersToProcess: any[] = [];

  try {
    if (req.file.originalname.endsWith(".csv")) {
      await new Promise<void>((resolve, reject) => {
        fs.createReadStream(filePath)
          .pipe(csvParser())
          .on("data", (data) => numbersToProcess.push(data))
          .on("end", () => resolve())
          .on("error", reject);
      });
    } else if (req.file.originalname.endsWith(".xlsx")) {
      const workbook = xlsx.readFile(filePath);
      const sheetName = workbook.SheetNames[0];
      const sheet = workbook.Sheets[sheetName];
      const data = xlsx.utils.sheet_to_json(sheet);
      numbersToProcess.push(...data);
    } else {
      fs.unlinkSync(filePath);
      return res.status(400).json({ error: "Invalid file format. Only CSV and XLSX are supported." });
    }

    let successCount = 0;
    let failedCount = 0;
    const globalState = await incrementGlobalVersion();

    for (const row of numbersToProcess) {
      const rawNumber = row.number || row.Number || row.Phone || Object.values(row)[0];
      if (!rawNumber) {
        failedCount++;
        continue;
      }

      const normalized = normalizeNumber(rawNumber);
      if (!normalized) {
        failedCount++;
        continue;
      }

      try {
        await prisma.numberEntry.upsert({
          where: { normalizedNumber: normalized },
          update: {
            active: true,
            displayLabel: row.displayLabel || row.name || row.Name || "Bulk Upload",
            type: "BLOCKED",
            reasonCode: row.reasonCode || "FRAUD",
            severity: row.severity || "HIGH",
            source: "bulk_upload",
          },
          create: {
            normalizedNumber: normalized,
            displayLabel: row.displayLabel || row.name || row.Name || "Bulk Upload",
            type: "BLOCKED",
            reasonCode: row.reasonCode || "FRAUD",
            severity: row.severity || "HIGH",
            source: "bulk_upload",
            addedAtVersion: globalState.currentVersion,
            active: true,
          }
        });
        successCount++;
      } catch (err) {
        failedCount++;
      }
    }

    fs.unlinkSync(filePath);

    res.json({
      total: numbersToProcess.length,
      successCount,
      failedCount
    });

  } catch (error) {
    if (fs.existsSync(filePath)) fs.unlinkSync(filePath);
    res.status(500).json({ error: "Failed to process file" });
  }
};
