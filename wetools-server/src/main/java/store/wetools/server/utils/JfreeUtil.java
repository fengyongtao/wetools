package store.wetools.server.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import java.awt.*;
import java.io.FileOutputStream;

/**
 * 生成其他样式可见博客
 * https://blog.csdn.net/chehec2010/article/details/81026789
 */
public class JfreeUtil {

    /**
     *
     * @param data
     * @param rowKeys
     * @param columnKeys
     * @return
     */
    public static CategoryDataset getBarData(double[][] data, String[] rowKeys, String[] columnKeys) {
        return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
    }

    /**
     *
     * @param chartTitle
     * @param x
     * @param y
     * @param xyDataset
     * @param charPath
     * @param charName
     * @return
     */
    public static String createTimeXYChar(String chartTitle, String x, String y, CategoryDataset xyDataset,  String charPath, String charName) {

        JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y, xyDataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setTextAntiAlias(false);
        chart.setBackgroundPaint(Color.WHITE);
        // 设置图标题的字体重新设置title
        Font font = new Font("隶书", Font.BOLD, 25);
        TextTitle title = new TextTitle(chartTitle);
        title.setFont(font);
        chart.setTitle(title);
        // 设置面板字体
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        // x轴 // 分类轴网格是否可见
        categoryplot.setDomainGridlinesVisible(true);
        // y轴 //数据轴网格是否可见
        categoryplot.setRangeGridlinesVisible(true);
        // 虚线色彩
        categoryplot.setRangeGridlinePaint(Color.WHITE);
        categoryplot.setDomainGridlinePaint(Color.WHITE);
        categoryplot.setBackgroundPaint(Color.lightGray);
        // 设置轴和面板之间的距离
        // categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        // 轴标题
        domainAxis.setLabelFont(labelFont);
        // 轴数值
        domainAxis.setTickLabelFont(labelFont);
        // 横轴上的
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        // Lable
        // 45度倾斜
        // 设置距离图片左端距离
        domainAxis.setLowerMargin(0.0);
        // 设置距离图片右端距离
        domainAxis.setUpperMargin(0.0);
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setAutoRangeIncludesZero(true);
        // 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
        lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
        lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
        // 显示折点数据
        lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineandshaperenderer.setBaseItemLabelsVisible(true);

        FileOutputStream fos_jpg = null;
        try {
            FileUtil.isChartPathExist(charPath);
            String chartName = charPath + charName;
            fos_jpg = new FileOutputStream(chartName);

            // 将报表保存为png文件
            ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 510);

            return chartName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fos_jpg.close();
                System.out.println("create time-createTimeXYChar.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
