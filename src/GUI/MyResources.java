package GUI;

import java.awt.event.KeyEvent;
import java.util.ListResourceBundle;

//Platesnėms studijoms:
//http://download.oracle.com/javase/6/docs/api/java/util/ListResourceBundle.html
//http://download.oracle.com/javase/tutorial/i18n/resbundle/concept.html
//Naudojame nustatytą lokalę.
public class MyResources extends ListResourceBundle {

    //Grąžinamas programos resursų masyvas. Kiti metodai paveldimi iš abstrakčios 
    //ListResourceBundle klasės.
    @Override
    public Object[][] getContents() {
        return contents;
    }
    //Tai tik didelis objektų masyvų masyvas, kuriame saugomi programos resursai
    static final Object[][] contents = {
        {"lblTitle", "KTU IF 2012. LD7. Hierarchinių duomenų struktūrų tiriamasis darbas"},
        {"lblAuthor", "<html><b>Autorius: Povilas Sidaravičius, IF-1/10</b><br>email: "
            + "<FONT COLOR=BLUE>povilas.sidaravicius@stud.ktu.lt</FONT></html>\n"
        },
        {"lblMenus", new String[]{
                "Failas",
                "Pagalba"
            }},
        {"lblMenuItems", new String[][]{
                {"Atidaryti..", "Išsaugoti..", "Išeiti"},
                {"Apie.."}
            }},
        {"lblBorders", new String[]{
                "Duomenų aibė medžio formos duomenų struktūroje",
                "Rezultatai",
                "Parametrai",
                "Programos vykdymas"
            }},
        {"cmbTreeTypes", new String[]{
                "DP-medis",
                "AVL-Medis",
                "Kiti medžiai"
            }},
        {"cmbTreeSymbols", new String[]{
                "Apskritimas",
                "Kvadratas"
            }},
        {"btnLabels", new String[]{
                "Generuoti vietų aibę",
                "Peržiūra su iteratoriumi",
                "Papildyti aibę vietų",
                "Greitaveikos tyrimas",
                "Pašalinti vietą iš aibės",
                "Medžio aukštis",
                "Head Set",
                "Tail Set",
                "Sub Set",
                "Funkcija"
            }},
        {"lblParams1", new String[]{
                "Paieškos medžio tipas",
                "Elemento simbolis medyje",
                "Elemento kirtiklis",
                "Duomenų įvedimas"
            }},
        {"tfParams1", new String[]{
                "",
                "",
                "",
                ""
            }},
        {"errMsgs1", new String[]{
                "",
                "",
                "",
                "",}},
        {"lblParams2", new String[]{
                "Generuojamos vietų aibės dydis",
                "Pradinė vietų aibės imtis",
                "Likęs vietų aibės dydis",
                "Išbarstymo koeficientas",
                "Sub Set Tarpas",
                "Tevo nr"
            }},
        {"tfParams2", new String[]{
                "100",
                "10",
                "",
                "0.8",
                ""
            }},
        {"errMsgs2", new String[]{
                "Netinkama aibės imtis arba netinkamai nuskaityti\n   duomenys",
                "Netinkamas generuojamos aibės dydis",
                "Netinkamas išbarstymo koeficientas",
                "Generuojamos aibės dydis turi būti 3 kartus\ndidesnis negu pradinė aibės imtis",}},
        {"msgs", new String[]{
                "Dar neįdiegta",
                "Greitaveikos tyrimas",
                "Visa sugeneruota aibė jau išspausdinta",
                "Failas perskaitytas.",
                "Duomenų aibė medyje",
                "Duomenų aibė",
                "Aibės papildymas elementu",
                "Medžio papildymas elementu",
                "Elemento pašalinimas iš aibės",
                "Aibės peržiūra su iteratoriumi",
                "Aibė tuščia",
                "Sisteminė klaida. Žiūrėti konsolėje",
                "Medžio aukštis",
                "Head Set",
                "Tail Set",
                "Sub Set"
            }},
        {"keys", new int[][]{
                {KeyEvent.VK_O, KeyEvent.VK_S, KeyEvent.VK_X},
                {-1}
            }}
    };
}