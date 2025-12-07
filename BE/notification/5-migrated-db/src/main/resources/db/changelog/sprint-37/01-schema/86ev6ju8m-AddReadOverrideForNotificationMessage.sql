ALTER TABLE "NotificationMessage"
  ADD COLUMN IF NOT EXISTS "readOverride" BOOLEAN;

CREATE INDEX IF NOT EXISTS "NotificationMessage_subscriberId_readOverride"
  ON "NotificationMessage" ("subscriberId", "readOverride");
