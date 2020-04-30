from pyspark import SparkFiles

if __name__ == "__main__":
  print("Hello World")
  path = os.path.join(tempdir, "test.txt")
  with open(path, "w") as testFile:
    _ = testFile.write("100")
