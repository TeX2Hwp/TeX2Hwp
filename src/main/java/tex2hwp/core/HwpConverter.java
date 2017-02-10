package tex2hwp.core;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HwpConverter {
    public HwpConverter() {
    }

    public String convertNode(Node node) {
        String type = node.getNodeName();
        String value = node.getTextContent();
        NodeList childs = node.getChildNodes();

        switch (value) {
            case "π":
                value = "pi";
                break;
            case "∫":
                value = "int";
                break;
        }

        switch (type) {
            case "math":
                return convertNodeList(childs);
            case "mrow":
                return convertNodeList(childs);
            case "mfrac":
                return String.format("{ %s } over { %s }",
                        convertNode(childs.item(0)), convertNode(childs.item(1)));
            case "msqrt":
                return String.format("sqrt { %s }",
                        convertNode(childs.item(0)));
            case "mroot":
                return String.format("root { %s } of { %s }",
                        convertNode(childs.item(0)), convertNode(childs.item(1)));
            case "mfenced":
                return String.format("( %s )", convertNodeList(childs));
            case "msup":
                return String.format("{ %s } ^ { %s }",
                        convertNode(childs.item(0)), convertNode(childs.item(1)));
            case "msub":
                return String.format("{ %s } _ { %s }",
                        convertNode(childs.item(0)), convertNode(childs.item(1)));
            case "msubsup":
                return String.format("{ %s } _ { %s } ^ { %s }",
                        convertNode(childs.item(0)), convertNode(childs.item(1)), convertNode(childs.item(2)));

            case "munder":
                // TODO : convert munder
            case "mover":
                // TODO : convert mover
            case "munderover":
                // TODO : convert munderover
            default:
                if (childs.getLength() == 0) {
                    return value;
                } else {
                    return convertNodeList(childs);
                }
        }
    }

    public String convertNodeList(NodeList nodes) {
        StringBuilder builder = new StringBuilder();
        int nodeCount = nodes.getLength();

        for (int i = 0; i < nodeCount - 1; i++) {
            builder.append(convertNode(nodes.item(i)));
            builder.append(" ");
        }

        if (nodeCount > 0) {
            builder.append(convertNode(nodes.item(nodeCount - 1)));
        }

        return builder.toString();
    }
}
