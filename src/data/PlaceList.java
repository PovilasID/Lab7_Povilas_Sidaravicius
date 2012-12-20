/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import studijosKTU.BstSetKTU;
import studijosKTU.SetADT;

/**
 *
 * @author PovilasSid
 */
public class PlaceList {
        public static SetADT<String> placeTypes(Place[] bi) {
        SetADT<Place> uni = new BstSetKTU<>(Place.usingType);
        SetADT<String> kart = new BstSetKTU<>();
        for (Place a : bi) {
            if (!uni.add(a)) {
                kart.add(a.getType());
            }
        }
        return kart;
    }
}