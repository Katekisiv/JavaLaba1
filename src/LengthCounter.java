import java.io.*;

public class LengthCounter {
    String delimiter;
    String combiner;

    public LengthCounter(String delimiter, String combiner) {
        this.delimiter = delimiter;
        this.combiner = combiner;
    }

    public void countFromFile(String path1, String path2) throws IOException {
        BufferedReader file1 = new BufferedReader(new FileReader(path1));
        BufferedWriter file2 = new BufferedWriter(new FileWriter(path2));

        String line;
        while((line = file1.readLine()) != null) {
            file2.write(count(line) + "\n");
        }
        file1.close();
        file2.close();
    }

    public String count(String s) {
        StringBuilder line = new StringBuilder();

        int delimiterIndex = findDelimiter(s);
        if (delimiterIndex == -1) {
            if(isInsideQuotes(s)){
                line.append(s.length() - 2);
            } else {
                line.append(s.length());
            }
        } else  {
            if (isInsideQuotes(s.substring(0, delimiterIndex))) {
                line.append(findDelimiter(s) - 2);
            } else {
                line.append(findDelimiter(s));
            }

            line.append(this.combiner);
            line.append(count(s.substring(delimiterIndex + 1)));
        }

        return line.toString();
    }

    private int findDelimiter(String s) {
        int firstDelimiter = s.indexOf(this.delimiter);
        if (firstDelimiter == -1) {
            return -1;
        }

        while (true) {
            int indexQuote = findClosingQuote(s);

            if(indexQuote == -1 || firstDelimiter == -1) {
                return firstDelimiter;
            }

            if(indexQuote == firstDelimiter - 1) {
                return firstDelimiter;
            } else if(indexQuote > firstDelimiter) {
                firstDelimiter = s.indexOf(this.delimiter, indexQuote);
            } else if(indexQuote < firstDelimiter) {
                //if the amount of quotes is odd - quotes isn't closed
                if (s.substring(0, firstDelimiter).chars().filter(ch -> ch == '"').count() % 2 != 0) {
                    firstDelimiter = s.indexOf(this.delimiter, indexQuote);
                } else {
                    //if the amount of quotes is pair - they are closed, so delimiter isn't inside
                    return firstDelimiter;
                }
            }
        }
    }

    private int findClosingQuote(String s) {
        if (s.charAt(0) == '"') {
            return s.indexOf('"', 1);
        } else {
            return -1;
        }
    }

    private boolean isInsideQuotes(String s) {
        return s.length() != 0 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"';
    }
}
