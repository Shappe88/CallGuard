import { PrismaClient } from "@prisma/client";

export const prisma = new PrismaClient();

export const getGlobalState = async () => {
  let state = await prisma.globalState.findFirst();
  if (!state) {
    state = await prisma.globalState.create({
      data: { id: 1, currentVersion: 2 }
    });
  }
  return state;
};

export const incrementGlobalVersion = async () => {
  const state = await getGlobalState();
  return prisma.globalState.update({
    where: { id: state.id },
    data: { currentVersion: { increment: 1 } }
  });
};
