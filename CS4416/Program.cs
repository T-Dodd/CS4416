using System;

public class Scanner {
    static void Main(string[] args) {
        // Specify the path to your text file
        string filePath = "example.txt"; // Replace with the actual file path

        try
        {
            // Read the contents of the text file into a string
            string fileContents = File.ReadAllText(filePath);

            // Print the contents to the console (for demonstration)
            Console.WriteLine("File Contents:");
            Console.WriteLine(fileContents);

            // You can now work with the 'fileContents' string in your program
        }
        catch (IOException e)
        {
            Console.WriteLine($"An error occurred while reading the file: {e.Message}");
        }
    }
}