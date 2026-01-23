/**
 * Attendance Tracker - Demon Slayer Edition
 * Author: Coloma, Aysberg Matt A.
 */

// --- 1. LOGIN SYSTEM ---
// --- 1. LOGIN SYSTEM ---
function handleLogin() {
    const user = document.getElementById('username').value.trim();
    const pass = document.getElementById('password').value.trim();
    const audio = document.getElementById('bg-music'); // Match the ID in HTML

    // Requirement: Check if fields are not empty
    if (user !== "" && pass !== "") {
        
        // PLAY AUDIO ON LOGIN CLICK
        if (audio) {
            audio.loop = false;
            audio.volume = 0.5;
            audio.play().catch(error => {
                console.log("Playback failed. This usually happens if the file path is wrong.");
            });
        }

        // Hide Login, Show Dashboard
        document.getElementById('login-section').style.display = 'none';
        document.getElementById('main-app').style.display = 'block';
        
        // Auto-fill the name field
        document.getElementById('nameField').value = user;
        
        // Initialize metadata (Timestamp and Sig)
        updateMetadata();
        
    } else {
        // If they try to log in with empty fields, play the sound as an alert
        if (audio) {
            audio.play();
        }
        alert("Identify yourself, Slayer!");
    }
}
// --- 2. METADATA GENERATOR (TIMESTAMP & SIG) ---
function updateMetadata() {
    const now = new Date();
    
    // Step 4: Capture and Display System Time
    const options = { 
        year: 'numeric', month: '2-digit', day: '2-digit', 
        hour: '2-digit', minute: '2-digit', second: '2-digit', 
        hour12: false 
    };
    const timestamp = now.toLocaleString('en-US', options);
    
    // Generate E-Signature (Unique Seal ID)
    const randomSig = "DS-" + Math.random().toString(36).substring(2, 10).toUpperCase();

    // Display values in the read-only boxes
    document.getElementById('timeField').value = timestamp;
    document.getElementById('sigField').value = randomSig;
}

// --- 3. ATTENDANCE SUMMARY & FILE GENERATION ---
function submitAttendance() {
    const name = document.getElementById('nameField').value.trim();
    const course = document.getElementById('courseField').value.trim();
    const timestamp = document.getElementById('timeField').value;
    const signature = document.getElementById('sigField').value;
    const sound = document.getElementById('AnimeWow');

    if (name === "" || course === "") {
        if (sound) sound.play().catch(() => {});
        alert("The record is incomplete! Fill in your Course/Year.");
        return;
    }

    // Step 4: Display in the Ledger Box (Who logged in + Timestamp + Sig)
    const list = document.getElementById('attendanceList');
    if (list) {
        const entry = document.createElement('li');
        entry.className = "ledger-entry";
        
        entry.innerHTML = `
            <div class="entry-header"><strong>Slayer:</strong> ${name}</div>
            <div class="entry-details">
                <span><strong>Time:</strong> ${timestamp}</span><br>
                <span><strong>Seal:</strong> <i style="color: #b02a2a;">${signature}</i></span><br>
                <span><strong>Unit:</strong> ${course}</span>
            </div>
        `;
        
        // Add newest entry to the top of the scroll
        list.prepend(entry);
    }

    // Step 5: Generate File Output (Blob API)
    const attendanceSummary = 
        `--- SLAYER CORPS ATTENDANCE ---\n` +
        `NAME: ${name}\n` +
        `COURSE: ${course}\n` +
        `TIMESTAMP: ${timestamp}\n` +
        `E-SIGNATURE: ${signature}\n` +
        `-------------------------------`;

    const blob = new Blob([attendanceSummary], { type: 'text/plain' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = `${name}_attendance.txt`;
    link.click();

    // Reset fields and refresh metadata for next submission
    document.getElementById('courseField').value = "";
    updateMetadata(); 
    alert("Record sent to the Crow!");
}