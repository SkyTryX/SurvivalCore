package fr.skytryx.survivalcore;

import fr.skytryx.survivalcore.skills.Skills;
import fr.skytryx.survivalcore.skills.TypeMetier;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Map;

public class Joueur {
Player player;
Map<TypeMetier, Skills> skills;
public Joueur(Player player) {

}

}
