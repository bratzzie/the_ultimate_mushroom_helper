package org.nau.diploma.chantarellecheck.repositories.adapters;

import org.nau.diploma.chantarellecheck.SimilarSpeciesItem;
import org.nau.diploma.chantarellecheck.RecycleViewItem;
import org.nau.diploma.chantarellecheck.repositories.MushroomsRepository;

import java.util.List;

public class ServiceForLoadingMushrooms {
    private MushroomsRepository repository;

    public ServiceForLoadingMushrooms(MushroomsRepository repository) {
        this.repository = repository;
    }

    public RecycleViewItem getItemByName(String name) {
        return repository.getAllItems().stream().filter(item -> item.getName().toLowerCase().equals(name)).findFirst().orElse(null);
    }

    public String getOtherNamesById(String label) {
        RecycleViewItem item = getItemByName(label);
        return String.join(", ", item.getCommonNames()) + String.join(", ", item.getScientificNames());
    }

    public List<SimilarSpeciesItem> getSimilarItems(String id) {
        return repository.getAllItems().stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null).getConfusionItems();
    }
}
