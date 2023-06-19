import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ChineseMedicationDataCrawler {
    public static void main(String[] args) {
        String url = "http://www.zhongyoo.com/"; // 替换为实际的数据源URL

        try {
            Document document = Jsoup.connect(url).get();

            // 使用选择器定位中药信息所在的HTML元素
            Elements medicationElements = document.select(".medication");

            for (Element medicationElement : medicationElements) {
                // 提取中药名称
                String name = medicationElement.select(".name").text();

                // 提取中药描述
                String description = medicationElement.select(".description").text();

                // 提取中药用法
                String usage = medicationElement.select(".usage").text();

                // 打印中药信息
                System.out.println("名称：" + name);
                System.out.println("描述：" + description);
                System.out.println("用法：" + usage);
                System.out.println("-------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
