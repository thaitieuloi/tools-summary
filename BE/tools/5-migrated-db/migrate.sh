#!/bin/bash
# Database Migration Script for Linux/Mac
# Usage: ./migrate.sh [profile]

PROFILE=${1:-default}

echo "============================================================"
echo "Running Database Migration"
echo "Profile: $PROFILE"
echo "============================================================"

cd "$(dirname "$0")/../.."

if [ "$PROFILE" = "default" ]; then
    ./gradlew :migrated-db:migrate
else
    ./gradlew :migrated-db:migrate -Pprofile="$PROFILE"
fi

if [ $? -ne 0 ]; then
    echo ""
    echo "============================================================"
    echo "Migration FAILED!"
    echo "============================================================"
    exit 1
fi

echo ""
echo "============================================================"
echo "Migration completed successfully!"
echo "============================================================"
