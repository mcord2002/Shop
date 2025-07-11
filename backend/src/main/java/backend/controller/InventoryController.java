package backend.controller;

import backend.exception.InventoryNotFoundException;
import backend.model.InventoryModel;
import backend.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/img/";

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostMapping("")
    public InventoryModel newInventoryModel(@RequestBody InventoryModel newInventoryModel) {
        return inventoryRepository.save(newInventoryModel);
    }

    @PostMapping("/itemImg")
    public String itemImage(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File destination = new File(UPLOAD_DIR + fileName);
            file.transferTo(destination);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading image: " + e.getMessage());
        }
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable String name) throws MalformedURLException {
        Path path = Paths.get(UPLOAD_DIR + name);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("")
    public List<InventoryModel> getAllItem() {
        return inventoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public InventoryModel getItemId(@PathVariable Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }

    @PutMapping("/{id}")
    public InventoryModel updateItem(
            @RequestPart(value = "itemDetails") String itemDetails,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable Long id
    ) {
        ObjectMapper mapper = new ObjectMapper();
        InventoryModel newInventory;
        try {
            newInventory = mapper.readValue(itemDetails, InventoryModel.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing itemDetails", e);
        }

        return inventoryRepository.findById(id).map(existingInventory -> {
            existingInventory.setItemId(newInventory.getItemId());
            existingInventory.setItemName(newInventory.getItemName());
            existingInventory.setItemCategory(newInventory.getItemCategory());
            existingInventory.setItemQty(newInventory.getItemQty());
            existingInventory.setItemDetails(newInventory.getItemDetails());
            existingInventory.setItemPrice(newInventory.getItemPrice());


            if (file != null && !file.isEmpty()) {
                String itemImage = file.getOriginalFilename();
                try {
                    File dir = new File(UPLOAD_DIR);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    file.transferTo(new File(UPLOAD_DIR + itemImage));
                    existingInventory.setItemImage(itemImage);
                } catch (IOException e) {
                    throw new RuntimeException("Error saving uploaded file", e);
                }
            }

            return inventoryRepository.save(existingInventory);
        }).orElseThrow(() -> new InventoryNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        InventoryModel inventoryItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));

        String itemImage = inventoryItem.getItemImage();
        if (itemImage != null && !itemImage.isEmpty()) {
            File imageFile = new File(UPLOAD_DIR + itemImage);
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Image Deleted");
                } else {
                    System.out.println("Failed to delete image");
                }
            }
        }
        inventoryRepository.deleteById(id);
        return "Item with id " + id + " and its image deleted";
    }

}
