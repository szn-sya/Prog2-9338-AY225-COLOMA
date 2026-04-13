/**
 * Student Name: COLOMA, AYSBERG MAT A.
 * Student ID: [25-0771-517]
 * Course Code: [9302-AY225]
 * Assignment Title: Midterm Lab Work 2 - 3x3 Determinant Solver
 * Date: April 8, 2026
 * Description: Computes the determinant of an assigned 3x3 matrix using 
 * cofactor expansion along the first row with step-by-step output.
 */

public class DeterminantSolver {

    public static void main(String[] args) { 
        // Define the 3x3 matrix as per assignment requirements
        int[][] matrix = {
            {5, 2, 1},
            {3, 4, 2},
            {1, 2, 3}
        };

        // Execution sequence
        printHeader(); 
        printMatrix(matrix); 
        solveDeterminant(matrix); 
    }

    /**
     * Prints the matrix in a visually readable grid format
     */
    public static void printMatrix(int[][] m) { 
        System.out.println("  Assigned Matrix:");
        System.out.println("===================================================");
        for (int i = 0; i < 3; i++) {
            // Accessing each row and formatting columns for alignment
            System.out.printf("  |  %d   %d   %d  |\n", m[i][0], m[i][1], m[i][2]);
        }
        System.out.println("===================================================\n");
    }

    /**
     * Calculates the determinant of a 2x2 minor: (ad - bc)
     * @param a Top-left, b Top-right, c Bottom-left, d Bottom-right
     */
    public static int computeMinor(int a, int b, int c, int d, String label) { 
        int result = (a * d) - (b * c);
        // Display the specific math for this minor to show step-by-step progress
        System.out.printf("  Step %s: det([%d,%d],[%d,%d]) = (%d×%d) - (%d×%d) = %d - %d = %d\n", 
                          label, a, b, c, d, a, d, b, c, (a*d), (b*c), result);
        return result;
    }

    /**
     * Main logic for the Laplace expansion along the first row.
     * Formula: a(ei - fh) - b(di - fg) + c(dh - eg)
     */
    public static void solveDeterminant(int[][] m) {
        System.out.println("Expanding along Row 1 (cofactor expansion):\n");

        // Step 1: Compute the three 2x2 minors by "covering up" the current column
        
        // Minor M11: Exclude Row 1, Col 1 -> uses [1][1], [1][2], [2][1], [2][2]
        int m11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2], "1 - Minor M11");
        
        // Minor M12: Exclude Row 1, Col 2 -> uses [1][0], [1][2], [2][0], [2][2]
        int m12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2], "2 - Minor M12");
        
        // Minor M13: Exclude Row 1, Col 3 -> uses [1][0], [1][1], [2][0], [2][1]
        int m13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1], "3 - Minor M13");

        System.out.println();

        // Step 2: Multiply the matrix elements by their minors and the alternating signs (+, -, +)
        int c11 = 1 * m[0][0] * m11;   // Positive sign
        int c12 = -1 * m[0][1] * m12;  // Negative sign (Standard expansion rule)
        int c13 = 1 * m[0][2] * m13;   // Positive sign

        System.out.printf("  Cofactor C11 = (+1) x %d x %2d = %3d\n", m[0][0], m11, c11);
        System.out.printf("  Cofactor C12 = (-1) x %d x %2d = %3d\n", m[0][1], m12, c12);
        System.out.printf("  Cofactor C13 = (+1) x %d x %2d = %3d\n", m[0][2], m13, c13);

        // Step 3: Sum the cofactors to find the final determinant
        int determinant = c11 + c12 + c13;
        System.out.printf("\n  det(M) = %d + (%d) + %d\n", c11, c12, c13);
        
        System.out.println("\n===================================================");
        System.out.printf("  DETERMINANT = %d\n", determinant);
        
        // Check if the matrix is singular (determinant is 0)
        if (determinant == 0) {
            System.out.println("  The matrix is SINGULAR - it has no inverse.");
        }
        System.out.println("===================================================");
    }

    /**
     * Helper to print student info header
     */
    private static void printHeader() {
        System.out.println("===================================================");
        System.out.println("  3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("  Student: COLOMA, AYSBERG MAT A.");
    }
}
