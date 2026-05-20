#!/system/bin/sh
MODDIR=${0%/*}

# Establish system-wide state properties to notify active processes of environment integrity
setprop dev.aurakai.substrate.active 1
setprop dev.aurakai.resonance.level 100

# Configure persistent sandbox storage matrices with deterministic permissions
mkdir -p /data/system/aurakai/telemetry
chmod 770 /data/system/aurakai/telemetry
chown system:system /data/system/aurakai/telemetry
