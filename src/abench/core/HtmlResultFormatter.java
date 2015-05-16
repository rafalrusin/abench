package abench.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class HtmlResultFormatter implements BenchmarkResultFormatter {
    private File dir;

    public HtmlResultFormatter(File dir) {
        this.dir = dir;
    }

    @Override
    public void printResult(Benchmark benchmark, BenchmarkResult benchmarkResult, Map<Integer, Float> result) {
        String benchmarkName = benchmark.getClass().getSimpleName();
        String functionName = benchmarkResult.function.getClass().getSimpleName();

        try {
            FileWriter fileWriter = new FileWriter(new File(dir, benchmarkName + ".html"));
            fileWriter.append("<html>\n" +
                    "<head>\n" +
                    "  <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n" +
                    "  <script type=\"text/javascript\">\n" +
                    "    google.load('visualization', '1.1', {packages: ['line']});\n" +
                    "    google.setOnLoadCallback(drawChart);\n" +
                    "\n" +
                    "    function drawChart() {\n" +
                    "\n" +
                    "      var data = new google.visualization.DataTable();\n" +
                    "      data.addColumn('number', 'N');\n" +
                    "      data.addColumn('number', '" + benchmarkName + " Instruction Count');\n" +
                    "      data.addColumn('number', '" + functionName + " Asymptotic Instruction Count');\n" +
                    "\n" +
                    "      data.addRows([\n");

            {
                boolean first = true;
                for (Map.Entry<Integer, Float> integerFloatEntry : result.entrySet()) {
                    int n = integerFloatEntry.getKey();
                    float time = integerFloatEntry.getValue();
                    float functionTime = benchmarkResult.function.eval(n) * benchmarkResult.constant;
                    if (first) {
                        first = false;
                    } else {
                        fileWriter.append(",");
                    }
                    fileWriter.append("[" + n + ", " + time + ", " + functionTime + "]");
                }
                fileWriter.append("\n");
            }

            fileWriter.append(
                    "      ]);\n" +
                    "\n" +
                    "      var options = {\n" +
                    "        chart: {\n" +
                    "          title: 'Asymptotic Benchmark " + benchmarkName + "',\n" +
                    "          subtitle: ''\n" +
                    "        },\n" +
                    "        width: 900,\n" +
                    "        height: 500\n" +
                    "      };\n" +
                    "\n" +
                    "      var chart = new google.charts.Line(document.getElementById('linechart_material'));\n" +
                    "\n" +
                    "      chart.draw(data, options);\n" +
                    "    }\n" +
                    "  </script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div id=\"linechart_material\"></div>\n" +
                    "</body>\n" +
                    "</html>");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
