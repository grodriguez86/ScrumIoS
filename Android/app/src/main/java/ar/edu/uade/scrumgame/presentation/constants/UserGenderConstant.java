package ar.edu.uade.scrumgame.presentation.constants;

public enum UserGenderConstant {
    FEMALE("Femenino"),
    MALE("Masculino");

    private String gender;

    UserGenderConstant(String gender) {
        this.gender = gender;
    }

    public static UserGenderConstant getGender(String genderText){
        UserGenderConstant[] genders = UserGenderConstant.values();
        for (UserGenderConstant gender : genders) {
            if(gender.gender.equals(genderText)){
                return gender;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid gender: %s",genderText));
    }
}
