// Step 1: Accept user inputs
let attendance = parseFloat(prompt("Enter Attendance Score:"));
let lab1 = parseFloat(prompt("Enter Lab Work 1 Grade:"));
let lab2 = parseFloat(prompt("Enter Lab Work 2 Grade:"));
let lab3 = parseFloat(prompt("Enter Lab Work 3 Grade:"));

// Step 2: Apply weighted formulas
let labAvg = (lab1 + lab2 + lab3) / 3;
let classStanding = (attendance * 0.40) + (labAvg * 0.60);

// Calculate required scores
let reqPass = (75 - (classStanding * 0.70)) / 0.30;
let reqExcl = (100 - (classStanding * 0.70)) / 0.30;

// Step 3: Evaluate academic standing
let statusMsg = "";
if (reqPass > 100) {
    statusMsg = "It is impossible to reach a passing grade this period.";
} else if (reqPass <= 0) {
    statusMsg = "You have already passed based on your current standing!";
} else {
    statusMsg = "Keep studying to hit your target!";
}

// Display results
alert(
    `--- Results ---\n` +
    `Attendance: ${attendance.toFixed(2)}\n` +
    `Lab Average: ${labAvg.toFixed(2)}\n` +
    `Class Standing: ${classStanding.toFixed(2)}\n\n` +
    `Required Exam Score to Pass: ${reqPass.toFixed(2)}\n` +
    `Required Exam Score for Excellent: ${reqExcl.toFixed(2)}\n\n` +
    `Remarks: ${statusMsg}`
);