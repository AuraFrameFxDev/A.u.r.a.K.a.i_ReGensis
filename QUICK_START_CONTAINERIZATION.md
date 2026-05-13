# 🚀 Containerization Quick Start Guide

## ✅ What's Been Completed

Your AURAKAI ReGenesis project is now containerization-ready! Here's what has been set up:

### Dockerfiles Created

1. **`app/ai_backend/Dockerfile`** - Python Flask Web Service
    - Multi-stage build (builder + production)
    - Base: python:3.11-slim
    - Runs on port 5000
    - Entry: gunicorn --workers=4

2. **`openrouter-agent/Dockerfile`** - Node.js TypeScript Service
    - Multi-stage build (builder + production)
    - Base: node:20-alpine
    - Runs on port 3000
    - Entry: node dist/headless.js

### Supporting Files

- ✅ `.dockerignore` files for both services
- ✅ `openrouter-agent/tsconfig.json` - TypeScript config
- ✅ `openrouter-agent/src/headless.ts` - Service entry point
- ✅ `.azure/containerization-plan.copilotmd` - Detailed plan
- ✅ `CONTAINERIZATION_SUMMARY.md` - This reference

---

## 🔨 Build Instructions

### Prerequisites

```powershell
# Install Docker Desktop (if not installed)
# https://www.docker.com/products/docker-desktop
```

### Build Both Images

```powershell
cd C:\Users\AuraF\AuraKai

# Build AI Backend
docker build -t aurakai-ai-backend:v1 -f app/ai_backend/Dockerfile .

# Build OpenRouter Agent  
docker build -t aurakai-openrouter-agent:v1 -f openrouter-agent/Dockerfile .
```

### Verify

```powershell
docker images | grep aurakai
# Shows both images with size info
```

---

## 🧪 Test Locally

```powershell
# Test Python Backend
docker run -p 5000:5000 \
-e GOOGLE_API_KEY = demo \
-e ANTHROPIC_API_KEY = demo \
aurakai-ai-backend:v1

# In another terminal:
curl http://localhost:5000/health

# Test Node.js Agent
docker run -p 3000:3000 \
-e OPENROUTER_API_KEY = demo \
aurakai-openrouter-agent:v1

# In another terminal:
curl http://localhost:3000/health
```

---

## ☁️ Deploy to Azure

### Option 1: Container Apps (Easiest)

```bash
az containerapp create \
  --name aurakai-backend \
  --resource-group my-rg \
  --image aurakaiacr.azurecr.io/aurakai-ai-backend:v1 \
  --target-port 5000 \
  --cpu 0.5 --memory 1.0Gi
```

### Option 2: Kubernetes (AKS)

```bash
# Generate K8s manifests
# Use: mcp_appmod-mcp-se_appmod-generate-k8s-manifest
# Then deploy with kubectl apply -f manifest.yaml
```

---

## 📊 Image Information

| Service          | Base             | Size (est.) | Port | User    |
|------------------|------------------|-------------|------|---------|
| AI Backend       | python:3.11-slim | 250-300 MB  | 5000 | genesis |
| OpenRouter Agent | node:20-alpine   | 150-200 MB  | 3000 | nodejs  |

---

## ✨ Features

✅ Production-ready multi-stage builds  
✅ Non-root user execution (security)  
✅ Health check endpoints  
✅ Graceful shutdown handling  
✅ Environment variable support  
✅ Layer caching optimized  
✅ Alpine/Slim base images (minimal)

---

## 📚 Documentation

- **Full Plan:** `.azure/containerization-plan.copilotmd`
- **Summary:** `CONTAINERIZATION_SUMMARY.md`
- **This Guide:** `QUICK_START_CONTAINERIZATION.md`

---

**Status:** ✅ Ready to Build | Next: Install Docker & Run Build Commands


