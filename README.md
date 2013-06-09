# Trakover

Trakover is a trivial yet useful Java program that scans a directory to find the mp3 files that have a size greater than 1MB.

# Why ?

Traktor software has issues with mp3 files with a size greater than 1MB. This program helps to find them.

# Will it resize my images ?

No, it will only tell you which files you should resize.

# How to use it ?

1. Build the project it creates a `trakover.jar` in the target directory
2. Run the jar with the path of the directory to scan as sole argument, the path must be absolute to work: `java -jar trakover.jar /musique`
