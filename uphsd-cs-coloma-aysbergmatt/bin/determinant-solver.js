/**
 * Student Name: COLOMA, AYSBERG MAT A.
 * Student ID: [25-0771-517]
 * Course Code: [9302-AY225]
 * Assignment Title: Midterm Lab Work 2 - 3x3 Determinant Solver
 * Date: April 8, 2026
 * Description: JavaScript version for calculating a 3x3 determinant.
 */

// Matrix hardcoded for Coloma
const matrix = [
    [5, 2, 1],
    [3, 4, 2],
    [1, 2, 3]
];

/**
 * Prints the matrix in a visual format
 */
const printMatrix = (m) => {
    console.log("  Assigned Matrix:");
    console.log("===================================================");
    m.forEach(row => {
        console.log(`  |  ${row[0]}   ${row[1]}   ${row[2]}  |`);
    });
    console.log("===================================================\n");
};

/**
 * Computes the 2x2 minor determinant
 */
const computeMinor = (a, b, c, d, label) => {
    const res = (a * d) - (b * c);
    console.log(`  Step ${label}: det([${a},${b}],[${c},${d}]) = (${a}×${d}) - (${b}×${c}) = ${a*d} - ${b*c} = ${res}`);
    return res;
};

/**
 * Main solver function using cofactor expansion
 */
const solveDeterminant = (m) => {
    console.log("Expanding along Row 1 (cofactor expansion):\n");

    // Perform minor calculations
    const m11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2], "1 — Minor M₁₁");
    const m12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2], "2 — Minor M₁₂");
    const m13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1], "3 — Minor M₁₃");

    console.log("");

    // Calculate cofactors
    const c11 = 1 * m[0][0] * m11;
    const c12 = -1 * m[0][1] * m12;
    const c13 = 1 * m[0][2] * m13;

    console.log(`  Cofactor C₁₁ = (+1) × ${m[0][0]} × ${String(m11).padStart(2)} = ${String(c11).padStart(3)}`);
    console.log(`  Cofactor C₁₂ = (-1) × ${m[0][1]} × ${String(m12).padStart(2)} = ${String(c12).padStart(3)}`);
    console.log(`  Cofactor C₁₃ = (+1) × ${m[0][2]} × ${String(m13).padStart(2)} = ${String(c13).padStart(3)}`);

    const det = c11 + c12 + c13;
    console.log(`\n  det(M) = ${c11} + (${c12}) + ${c13}`);

    console.log("\n===================================================");
    console.log(`  ✓  DETERMINANT = ${det}`);
    
    if (det === 0) {
        console.log("  The matrix is SINGULAR — it has no inverse.");
    }
    console.log("===================================================");
};

// Execution
console.log("===================================================");
console.log("  3x3 MATRIX DETERMINANT SOLVER");
console.log("  Student: COLOMA, AYSBERG MAT A.");
printMatrix(matrix);
solveDeterminant(matrix);