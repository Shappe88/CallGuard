import { Router } from "express";
import { login, getNumbers, addNumber, updateNumber, deleteNumber } from "../controllers/admin.controller";
import { authMiddleware } from "../middlewares/auth.middleware";

const router = Router();

router.post("/login", login);

router.use(authMiddleware);

router.get("/numbers", getNumbers);
router.post("/numbers", addNumber);
router.put("/numbers/:id", updateNumber);
router.delete("/numbers/:id", deleteNumber);

export default router;
