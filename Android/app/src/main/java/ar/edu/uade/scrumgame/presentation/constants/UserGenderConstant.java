package ar.edu.uade.scrumgame.presentation.constants;

import android.content.Context;

import java.util.HashMap;

import ar.edu.uade.scrumgame.R;

public enum UserGenderConstant {
    FEMALE("Femenino"),
    MALE("Masculino");

    private String gender;

    UserGenderConstant(String gender) {
        this.gender = gender;
    }

    public static UserGenderConstant getGender(String genderText, Context context) {
        String[] genderData = context.getResources().getStringArray(R.array.gender);
        HashMap<String, String> genderMap = new HashMap<>();
        for (int i = 0; i < genderData.length; i += 2) {
            genderMap.put(genderData[i], genderData[i + 1]);
        }
        UserGenderConstant[] genders = UserGenderConstant.values();
        String targetGender = genderMap.get(genderText);
        for (UserGenderConstant gender : genders) {
            if (gender.gender.equals(targetGender)) {
                return gender;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid gender: %s", genderText));
    }
}
