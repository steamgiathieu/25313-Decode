package org.firstinspires.ftc.team25313.subsystems.vision;

public class ObeliskPattern {

    public enum Color { GREEN, PURPLE }

    // trả về thứ tự 3 màu
    public static Color[] decode(int tagId) {
        switch (tagId) {
            case 21:
                return new Color[]{ Color.GREEN, Color.PURPLE, Color.PURPLE };

            case 22:
                return new Color[]{ Color.PURPLE, Color.GREEN, Color.PURPLE };

            case 23:
                return new Color[]{ Color.PURPLE, Color.PURPLE, Color.GREEN };

            default:
                return null;
        }
    }
}
