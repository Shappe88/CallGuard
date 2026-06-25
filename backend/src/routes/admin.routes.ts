import { Router } from "express";
import { login, getNumbers, addNumber, updateNumber, deleteNumber, bulkUpload } from "../controllers/admin.controller";
import { authMiddleware } from "../middlewares/auth.middleware";
import multer from "multer";
import os from "os";

const upload = multer({ dest: os.tmpdir() });

const router = Router();

router.post("/login", login);

router.use(authMiddleware);

router.get("/numbers", getNumbers);
router.post("/numbers", addNumber);
router.put("/numbers/:id", updateNumber);
router.delete("/numbers/:id", deleteNumber);
router.post("/upload-blacklist", upload.single("file"), bulkUpload);

export default router;
