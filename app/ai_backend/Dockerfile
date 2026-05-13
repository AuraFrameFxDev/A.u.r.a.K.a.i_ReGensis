# Multi-stage build for Genesis AI Backend
# Base stage with build dependencies
FROM python:3.11-slim as builder

WORKDIR /build

# Install build dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements and install Python dependencies
COPY requirements.txt .
RUN pip install --user --no-cache-dir -r requirements.txt

# Production stage
FROM python:3.11-slim

# Create non-root user for enhanced security
RUN groupadd -r genesis && useradd -r -g genesis genesis

WORKDIR /app

# Copy Python dependencies from builder
COPY --from=builder /root/.local /home/genesis/.local

# Set environment variables
ENV PATH=/home/genesis/.local/bin:$PATH \
    PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1 \
    PORT=5000 \
    FLASK_ENV=production

# Copy application code
COPY . .

# Change ownership to non-root user
RUN chown -R genesis:genesis /app
USER genesis

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD python -c "import requests; requests.get('http://localhost:5000/health')" || exit 1

# Expose port
EXPOSE 5000

# Start application with Gunicorn
CMD ["gunicorn", "--bind", "0.0.0.0:5000", "--workers", "4", "--timeout", "120", "--access-logfile", "-", "--error-logfile", "-", "genesis_api:app"]

