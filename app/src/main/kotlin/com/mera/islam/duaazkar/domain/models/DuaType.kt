package  com.mera.islam.duaazkar.domain.models

enum class DuaType(val type: Int) {
    ALL(0),
    Morning_Evening_Night(1),
    Wearing(2),
    Using_Bathroom(3),
    Home(4),
    Mosque(5),
    Ibadah(6),
    Decision_Making(7),
    Feelings(8),
    Doubt_in_Faith(9),
    Debt(10),
    Sin(11),
    Expelling_the_devil_and_his_whisperings(12),
    Mishap(13),
    Children(14),
    Sickness(15),
    Death(16),
    Weather(17),
    Sighting_the_crescent_moon(18),
    Eating(19),
    Guest(20),
    Seeing_premature_fruit(21),
    Marriage(22),
    Someone_in_trial(23),
    Gathering(24),
    Returning_a_supplication(25),
    One_who_does_you_a_favour(26),
    Protection_from_the_Dajjal(27),
    One_who_pronounces_his_love_for_you(28),
    One_who_has_offered_you_some_of_his_wealth(29),
    Fear_of_shirk(30),
    Someone_who_says_May_Allah_bless_you(31),
    Ascribing_things_to_evil_omens(32),
    Traveling(33),
    Recieving_good_or_bad_news(34),
    Sending_prayers_upon_prophet_ﷺ(35),
    Greetings(36),
    Sounds_of_animals(37),
    Praise(38),
    News(39),
    Evil_eye(40),
    Slaughter_Sacrifce(41),
    Devils(42),
    Forgiveness(43),
    Tasbih(44),
    Good_Manners(45);


    companion object {
        fun toDuaType(type: Int): DuaType {
            var dua = Morning_Evening_Night

            for (duaType in values()) {
                if (duaType.type == type) {
                    dua = duaType
                    break
                }
            }

            return dua
        }
    }
}