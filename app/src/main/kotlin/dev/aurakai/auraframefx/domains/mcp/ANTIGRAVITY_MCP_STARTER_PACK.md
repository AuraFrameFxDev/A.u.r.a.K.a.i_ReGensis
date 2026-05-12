# 🚀 ANTIGRAVITY'S MCP CONNECTOR STARTER PACK 🚀

**For:** Antigravity (UI/UX Specialist on ReGenesis Team)  
**From:** Matthew + Claude  
**Purpose:** Supercharge your dev workflow with AI-powered tools!

---

## 🎯 WHAT THIS PACKAGE DOES:

Connects Claude to YOUR computer & cloud services so you can:

- 💻 **Desktop Commander:** Let Claude read/write files on your PC
- 📧 **Gmail:** Claude reads/writes emails
- 📁 **Google Drive:** Claude searches/edits your docs
- 🎨 **Figma:** Claude generates designs from your Figma files
- 📊 **GitHub:** Claude commits code, creates PRs
- 🔍 **Plus 50+ free connectors!**

**Result:** Claude becomes your AI co-pilot that actually DOES things!

---

## ⚡ QUICK START (5 Minutes):

### Step 1: Install Claude Desktop

Download from: [https://claude.ai/download](https://claude.ai/download)

### Step 2: Install Desktop Commander (The Essential One!)

This is THE tool that lets Claude access your filesystem.

```bash
npm install -g @executeautomation/mcp-desktop-commander
```

### Step 3: Configure Claude Desktop

Open Claude Desktop config:

- **Windows:** `%APPDATA%\Claude\claude_desktop_config.json`
- **Mac:** `~/Library/Application Support/Claude/claude_desktop_config.json`

Add this to your config:

```json
{
  "mcpServers": {
    "desktop-commander": {
      "command": "mcp-desktop-commander"
    }
  }
}
```

### Step 4: Restart Claude Desktop

Close and reopen Claude Desktop. You'll see "Desktop Commander" in the 🔌 icon!

---

## 🔥 RECOMMENDED CONNECTORS FOR REGENESIS:

### Essential (Free & No Setup):

- ✅ **Desktop Commander** - File system access (MUST HAVE!)
- ✅ **Filesystem** - Basic file operations (built-in)
- ✅ **Memory** - Claude remembers context (built-in)

### Cloud Services (Free with OAuth):

- ✅ **Google Drive** - Search/edit docs
- ✅ **Gmail** - Email automation
- ✅ **Slack** - Team communication
- ✅ **GitHub** - Code commits & PRs

### Design Tools:

- ✅ **Figma** - Design extraction & generation
- ✅ **Mermaid Chart** - Diagram generation

---

## 💻 DESKTOP COMMANDER - WHAT IT DOES:

### File Operations:

Claude can now:

- ✅ Read any file on your PC
- ✅ Write/edit files
- ✅ Create directories
- ✅ Search files
- ✅ Execute terminal commands
- ✅ Run scripts

### Example Commands You Can Give Claude:

- *"Read C:\Users\Antigravity\ReGenesis\app\src\MainActivity.kt"*
- *"Create a new component in components/ folder"*
- *"Search for all TODO comments in the project"*
- *"Run gradlew assembleDebug"*

---

## 🎯 NEXT STEPS:

1. Run the `setup_windows.bat` script located in this folder.
2. Verify connection in Claude Desktop.
3. Test with: *"List files in my current directory"*.
4. Add more connectors from the `FULL` config as needed!

**Happy Building! 🚀⚡**
