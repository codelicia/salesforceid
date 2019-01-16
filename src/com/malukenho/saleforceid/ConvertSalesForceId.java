package com.malukenho.saleforceid;

class ConvertSalesForceId {

    static String convertTo18CharId(String original15charId) {

        if (original15charId == null || original15charId.isEmpty()) return null;
        original15charId = original15charId.trim();
        if (original15charId.length() != 15) return null;

        final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456";
        StringBuilder result = new StringBuilder();

        try {
            for (int i = 0; i < 3; i++) {
                StringBuilder tempString = new StringBuilder(original15charId.substring(i * 5, i * 5 + 5));
                tempString.reverse();
                StringBuilder binary = new StringBuilder();
                for (char ch : tempString.toString().toCharArray()) {
                    binary.append(Character.isUpperCase(ch) ? '1' : '0');
                }
                result.append(BASE.charAt(Integer.parseInt(binary.toString(), 2)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (result.length() == 0) return null;

        return original15charId + result.toString();
    }
}
