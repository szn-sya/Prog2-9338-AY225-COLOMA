/**
 * Attendance Tracker - Demon Slayer Edition
 * Author: Coloma, Aysberg Matt A.
 */

let activeSlayers = new Set(); 
const MAX_USERS = 3;

function handleLogin() {
    const user = document.getElementById('username').value.trim();
    const pass = document.getElementById('password').value.trim();
    const audio = document.getElementById('bg-music'); 

    if (user !== "" && pass !== "") {
        if (audio) {
            audio.loop = false;
            audio.volume = 0.5;
            audio.play().catch(e => console.log("Audio blocked by browser."));
        }

        document.getElementById('login-section').style.display = 'none';
        document.getElementById('main-app').style.display = 'block';
        document.getElementById('nameField').value = user;
        
        updateMetadata();
    } else {
        alert("Identify yourself, Slayer!");
    }
}

function updateMetadata() {
    const now = new Date();
    const options = { 
        year: 'numeric', month: '2-digit', day: '2-digit', 
        hour: '2-digit', minute: '2-digit', second: '2-digit', 
        hour12: false 
    };
    
    document.getElementById('timeField').value = now.toLocaleString('en-US', options);
    document.getElementById('sigField').value = "DS-" + Math.random().toString(36).substring(2, 10).toUpperCase();
    document.getElementById('user-count-display').innerText = `Active Slayers: ${activeSlayers.size} / ${MAX_USERS}`;
}

function submitAttendance() {
    const name = document.getElementById('nameField').value.trim();
    const course = document.getElementById('courseField').value; // Locked value
    const status = document.getElementById('statusField').value;
    const timestamp = document.getElementById('timeField').value;
    const signature = document.getElementById('sigField').value;

    // 3-User Limit Logic
    if (status === "TIME IN") {
        if (activeSlayers.size >= MAX_USERS && !activeSlayers.has(name)) {
            alert("Barracks Full! Only 3 Slayers allowed.");
            return;
        }
        activeSlayers.add(name);
    } else {
        activeSlayers.delete(name);
    }

    // Update Ledger
    const list = document.getElementById('attendanceList');
    const entry = document.createElement('li');
    entry.className = "ledger-entry";
    const statusColor = (status === "TIME IN") ? "#2e7d32" : "#b02a2a";

    entry.innerHTML = `
        <div class="entry-header">
            <strong>${name}</strong> 
            <span style="color: ${statusColor}; float: right;">${status}</span>
        </div>
        <div class="entry-details">
            <span><strong>Time:</strong> ${timestamp}</span><br>
            <span><strong>Unit:</strong> ${course}</span>
        </div>
    `;
    list.prepend(entry);

    // File Generation
    const summary = `NAME: ${name}\nUNIT: ${course}\nSTATUS: ${status}\nTIME: ${timestamp}\nSEAL: ${signature}`;
    const blob = new Blob([summary], { type: 'text/plain' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = `${name}_${status}.txt`;
    link.click();

    updateMetadata(); 
    alert(`Attendance logged for ${name}!`);
}