package org.baibei.assis.obj;

import org.baibei.console.ConsoleOutput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class VoiceToKeyboard {

    private static Robot robot;
    private static Map<Character, KeyStroke> keyMap;

    public VoiceToKeyboard() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Ошибка инициализации Robot", e);
        }

        keyMap = new HashMap<>();
        initKeyMap();
    }

    public static void init() {
        try {
            System.setProperty("java.awt.headless", "false");
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Ошибка инициализации Robot", e);
        }

        keyMap = new HashMap<>();
        initKeyMap();
    }

    private static void initKeyMap() {
        keyMap.put(',', new KeyStroke(KeyEvent.VK_COMMA, false));
        keyMap.put('.', new KeyStroke(KeyEvent.VK_PERIOD, false));
        keyMap.put(' ', new KeyStroke(KeyEvent.VK_SPACE, false));
        keyMap.put('\n', new KeyStroke(KeyEvent.VK_ENTER, false));
        keyMap.put('\\', new KeyStroke(KeyEvent.VK_BACK_SLASH, false));
        keyMap.put('|', new KeyStroke(KeyEvent.VK_BACK_SLASH, true));
        keyMap.put('/', new KeyStroke(KeyEvent.VK_SLASH, false));
        keyMap.put('?', new KeyStroke(KeyEvent.VK_SLASH, true));
        keyMap.put('-', new KeyStroke(KeyEvent.VK_MINUS, false));
        keyMap.put('_', new KeyStroke(KeyEvent.VK_MINUS, true));
        keyMap.put('=', new KeyStroke(KeyEvent.VK_EQUALS, false));
        keyMap.put('+', new KeyStroke(KeyEvent.VK_EQUALS, true));
        keyMap.put('\"', new KeyStroke(KeyEvent.VK_QUOTE, true));
        keyMap.put(';', new KeyStroke(KeyEvent.VK_SEMICOLON, false));
        keyMap.put(':', new KeyStroke(KeyEvent.VK_SEMICOLON, true));
        keyMap.put('<', new KeyStroke(KeyEvent.VK_COMMA, true));
        keyMap.put('>', new KeyStroke(KeyEvent.VK_PERIOD, true));
        keyMap.put('`', new KeyStroke(KeyEvent.VK_BACK_QUOTE, false));
        keyMap.put('~', new KeyStroke(KeyEvent.VK_BACK_QUOTE, true));
        keyMap.put('[', new KeyStroke(KeyEvent.VK_OPEN_BRACKET, false));
        keyMap.put('{', new KeyStroke(KeyEvent.VK_OPEN_BRACKET, true));
        keyMap.put(']', new KeyStroke(KeyEvent.VK_CLOSE_BRACKET, false));
        keyMap.put('}', new KeyStroke(KeyEvent.VK_CLOSE_BRACKET, true));
        keyMap.put('ё', new KeyStroke(KeyEvent.VK_ENTER, false));

        keyMap.put('1', new KeyStroke(KeyEvent.VK_1, false));
        keyMap.put('2', new KeyStroke(KeyEvent.VK_2, false));
        keyMap.put('3', new KeyStroke(KeyEvent.VK_3, false));
        keyMap.put('4', new KeyStroke(KeyEvent.VK_4, false));
        keyMap.put('5', new KeyStroke(KeyEvent.VK_5, false));
        keyMap.put('6', new KeyStroke(KeyEvent.VK_6, false));
        keyMap.put('7', new KeyStroke(KeyEvent.VK_7, false));
        keyMap.put('8', new KeyStroke(KeyEvent.VK_8, false));
        keyMap.put('9', new KeyStroke(KeyEvent.VK_9, false));
        keyMap.put('0', new KeyStroke(KeyEvent.VK_0, false));

        keyMap.put('!', new KeyStroke(KeyEvent.VK_1, true));
        keyMap.put('@', new KeyStroke(KeyEvent.VK_2, true));
        keyMap.put('#', new KeyStroke(KeyEvent.VK_3, true));
        keyMap.put('$', new KeyStroke(KeyEvent.VK_4, true));
        keyMap.put('%', new KeyStroke(KeyEvent.VK_5, true));
        keyMap.put('^', new KeyStroke(KeyEvent.VK_6, true));
        keyMap.put('&', new KeyStroke(KeyEvent.VK_7, true));
        keyMap.put('*', new KeyStroke(KeyEvent.VK_8, true));
        keyMap.put('(', new KeyStroke(KeyEvent.VK_9, true));
        keyMap.put(')', new KeyStroke(KeyEvent.VK_0, true));

        keyMap.put('a', new KeyStroke(KeyEvent.VK_A, false));
        keyMap.put('b', new KeyStroke(KeyEvent.VK_B, false));
        keyMap.put('c', new KeyStroke(KeyEvent.VK_C, false));
        keyMap.put('d', new KeyStroke(KeyEvent.VK_D, false));
        keyMap.put('e', new KeyStroke(KeyEvent.VK_E, false));
        keyMap.put('f', new KeyStroke(KeyEvent.VK_F, false));
        keyMap.put('g', new KeyStroke(KeyEvent.VK_G, false));
        keyMap.put('h', new KeyStroke(KeyEvent.VK_H, false));
        keyMap.put('i', new KeyStroke(KeyEvent.VK_I, false));
        keyMap.put('j', new KeyStroke(KeyEvent.VK_J, false));
        keyMap.put('k', new KeyStroke(KeyEvent.VK_K, false));
        keyMap.put('l', new KeyStroke(KeyEvent.VK_L, false));
        keyMap.put('m', new KeyStroke(KeyEvent.VK_M, false));
        keyMap.put('n', new KeyStroke(KeyEvent.VK_N, false));
        keyMap.put('o', new KeyStroke(KeyEvent.VK_O, false));
        keyMap.put('p', new KeyStroke(KeyEvent.VK_P, false));
        keyMap.put('q', new KeyStroke(KeyEvent.VK_Q, false));
        keyMap.put('r', new KeyStroke(KeyEvent.VK_R, false));
        keyMap.put('s', new KeyStroke(KeyEvent.VK_S, false));
        keyMap.put('t', new KeyStroke(KeyEvent.VK_T, false));
        keyMap.put('u', new KeyStroke(KeyEvent.VK_U, false));
        keyMap.put('v', new KeyStroke(KeyEvent.VK_V, false));
        keyMap.put('w', new KeyStroke(KeyEvent.VK_W, false));
        keyMap.put('x', new KeyStroke(KeyEvent.VK_X, false));
        keyMap.put('y', new KeyStroke(KeyEvent.VK_Y, false));
        keyMap.put('z', new KeyStroke(KeyEvent.VK_Z, false));

        keyMap.put('A', new KeyStroke(KeyEvent.VK_A, true));
        keyMap.put('B', new KeyStroke(KeyEvent.VK_B, true));
        keyMap.put('C', new KeyStroke(KeyEvent.VK_C, true));
        keyMap.put('D', new KeyStroke(KeyEvent.VK_D, true));
        keyMap.put('E', new KeyStroke(KeyEvent.VK_E, true));
        keyMap.put('F', new KeyStroke(KeyEvent.VK_F, true));
        keyMap.put('G', new KeyStroke(KeyEvent.VK_G, true));
        keyMap.put('H', new KeyStroke(KeyEvent.VK_H, true));
        keyMap.put('I', new KeyStroke(KeyEvent.VK_I, true));
        keyMap.put('J', new KeyStroke(KeyEvent.VK_J, true));
        keyMap.put('K', new KeyStroke(KeyEvent.VK_K, true));
        keyMap.put('L', new KeyStroke(KeyEvent.VK_L, true));
        keyMap.put('M', new KeyStroke(KeyEvent.VK_M, true));
        keyMap.put('N', new KeyStroke(KeyEvent.VK_N, true));
        keyMap.put('O', new KeyStroke(KeyEvent.VK_O, true));
        keyMap.put('P', new KeyStroke(KeyEvent.VK_P, true));
        keyMap.put('Q', new KeyStroke(KeyEvent.VK_Q, true));
        keyMap.put('R', new KeyStroke(KeyEvent.VK_R, true));
        keyMap.put('S', new KeyStroke(KeyEvent.VK_S, true));
        keyMap.put('T', new KeyStroke(KeyEvent.VK_T, true));
        keyMap.put('U', new KeyStroke(KeyEvent.VK_U, true));
        keyMap.put('V', new KeyStroke(KeyEvent.VK_V, true));
        keyMap.put('W', new KeyStroke(KeyEvent.VK_W, true));
        keyMap.put('X', new KeyStroke(KeyEvent.VK_X, true));
        keyMap.put('Y', new KeyStroke(KeyEvent.VK_Y, true));
        keyMap.put('Z', new KeyStroke(KeyEvent.VK_Z, true));
    }

    public static void type(String text, int speed) {
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; i++) {
            KeyStroke keyStroke = keyMap.get(c[i]);
            if (keyStroke == null) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                continue;
            }

            if (c[i] == 's') {
                if (c[i + 1] == 'p'
                        && c[i + 2] == 'a'
                        && c[i + 3] == 'c'
                        && c[i + 4] == 'e') {
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                    i += 4;
                } else {
                    if (keyStroke.shift) {
                        robot.keyPress(KeyEvent.VK_SHIFT);
                    }
                    robot.keyPress(keyStroke.keyCode);
                    robot.keyRelease(keyStroke.keyCode);
                    if (keyStroke.shift) {
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                }
            } else if (c[i] == 'e') {
                if (c[i + 1] == 'n'
                && c[i + 2] == 't'
                && c[i + 3] == 'e'
                && c[i + 4] == 'r') {
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    i += 4;
                } else {
                    if (keyStroke.shift) {
                        robot.keyPress(KeyEvent.VK_SHIFT);
                    }
                    robot.keyPress(keyStroke.keyCode);
                    robot.keyRelease(keyStroke.keyCode);
                    if (keyStroke.shift) {
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                }
            }
            else {
                if (keyStroke.shift) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(keyStroke.keyCode);
                robot.keyRelease(keyStroke.keyCode);
                if (keyStroke.shift) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            }
            robot.delay(speed);
        }
    }
}
