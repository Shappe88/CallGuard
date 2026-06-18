import path from "path";
import express from "express";
import cors from "cors";
import adminRoutes from "./routes/admin.routes";
import syncRoutes from "./routes/sync.routes";

const app = express();

app.use(cors());
app.use(express.json());

app.use(express.static(path.join(process.cwd(), "public")));

app.use("/api/v1/admin", adminRoutes);
app.use("/api/v1/blocklist", syncRoutes);

// Root endpoint
app.get("/", (req, res) => {
  res.sendFile(path.join(process.cwd(), "public/index.html"));
});

export default app;
