/**
 * Student Name: COLOMA, AYSBERG MATT A.
 * Machine Problems: MP01, MP13, MP19
 */

const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

rl.question('Enter the dataset file path (e.g., MOCK_DATA.csv): ', (filePath) => {
    try {
        const fileContent = fs.readFileSync(filePath, 'utf8');
        const lines = fileContent.split(/\r?\n/).filter(line => line.trim() !== "");
        
        if (lines.length === 0) {
            console.log("Error: File is empty.");
            rl.close();
            return;
        }

        // CSV Regex to handle commas inside quotes
        const csvSplitBy = /,(?=(?:(?:[^"]*"){2})*[^"]*$)/;

        const headers = lines[0].split(csvSplitBy).map(h => h.trim().replace(/"/g, ""));
        const records = lines.slice(1).map(line => line.split(csvSplitBy));

        // Execute Machine Problems
        displayMP01(records);
        const missingRows = findMP13(records);
        displayMP19(records, missingRows);

    } catch (err) {
        console.log("Error: " + err.message);
    } finally {
        rl.close();
    }
});

// [MP01] Basic record count
function displayMP01(data) {
    console.log(`\n[MP01] Total Records: ${data.length}`);
}

// [MP13] Logic to find rows with empty/null fields
function findMP13(data) {
    let rowsWithMissing = [];
    data.forEach((row, index) => {
        const hasMissing = row.some(field => !field || field.trim() === "" || field.trim() === '""');
        if (hasMissing) {
            rowsWithMissing.push(index + 1);
        }
    });
    console.log(`[MP13] Rows with missing values: [${rowsWithMissing.join(", ")}]`);
    return rowsWithMissing;
}

// [MP19] Summary Report
function displayMP19(data, missingRows) {
    console.log("\n--- [MP19] DATASET SUMMARY REPORT ---");
    console.log(`Total Records:   ${data.length}`);
    console.log(`Incomplete Rows: ${missingRows.length}`);
    console.log(`Data Integrity:  ${data.length - missingRows.length} complete rows.`);
    console.log("-------------------------------------");
}