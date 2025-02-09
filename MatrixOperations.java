import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class MatrixOperations {
    public static void main(String[] args) {
        if (args.length > 0) {
            handleCommandLineArgs(args);
        } else {
            handleUserInput();
        }
    }

    private static void handleCommandLineArgs(String[] args) {
        if (args.length == 1) {
            try {
                int size = Integer.parseInt(args[0]);
                generateAndMultiplyRandomMatrices(size);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer input");
            }
        } else if (args.length == 2) {
            multiplyMatricesFromFiles(args[0], args[1]);
        } else {
            System.out.println("Invalid number of arguments");
        }
    }

    private static void handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter two file names or one integer:");
        String input1 = scanner.next();
        
        try {
            int size = Integer.parseInt(input1);
            generateAndMultiplyRandomMatrices(size);
        } catch (NumberFormatException e) {
            String input2 = scanner.next();
            multiplyMatricesFromFiles(input1, input2);
        }
        scanner.close();
    }

    private static void generateAndMultiplyRandomMatrices(int size) {
        int[][] matrix1 = generateRandomMatrix(size, size);
        int[][] matrix2 = generateRandomMatrix(size, size);
        
        // Write matrices to files
        writeMatrixToFile(matrix1, "matrix1.txt");
        writeMatrixToFile(matrix2, "matrix2.txt");
        
        // Multiply matrices and save result
        int[][] result = multiplyMatrices(matrix1, matrix2);
        writeMatrixToFile(result, "matrix3.txt");
    }

    private static void multiplyMatricesFromFiles(String file1, String file2) {
        try {
            int[][] matrix1 = readMatrixFromFile(file1);
            int[][] matrix2 = readMatrixFromFile(file2);
            
            if (matrix1[0].length != matrix2.length) {
                System.out.println("Matrices cannot be multiplied. Invalid dimensions.");
                return;
            }
            
            int[][] result = multiplyMatrices(matrix1, matrix2);
            writeMatrixToFile(result, "matrix3.txt");
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    private static int[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10); // Generate numbers 0-9
            }
        }
        return matrix;
    }

    private static int[][] readMatrixFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int rows = 0;
        int cols = 0;
        
        // Count rows and columns
        while ((line = reader.readLine()) != null) {
            rows++;
            String[] numbers = line.trim().split("\\s+");
            cols = numbers.length;
        }
        reader.close();
        
        // Read the matrix
        int[][] matrix = new int[rows][cols];
        reader = new BufferedReader(new FileReader(filename));
        int row = 0;
        while ((line = reader.readLine()) != null) {
            String[] numbers = line.trim().split("\\s+");
            for (int col = 0; col < numbers.length; col++) {
                matrix[row][col] = Integer.parseInt(numbers[col]);
            }
            row++;
        }
        reader.close();
        return matrix;
    }

    private static void writeMatrixToFile(int[][] matrix, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int[] row : matrix) {
                for (int j = 0; j < row.length; j++) {
                    writer.print(row[j]);
                    if (j < row.length - 1) {
                        writer.print(" ");
                    }
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        
        int[][] result = new int[rows1][cols2];
        
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }
}