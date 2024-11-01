package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.service.ProductService;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private WholesalerProductService wholesalerProductService;

    /**
     * Get all products for consumers
     * @return product details
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('CONSUMER','WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {
        return productService.getAllProducts();
    }

    /**
     * get wholesalers by pid for consumers
     * @return List of wholesalers
     */
    @GetMapping("/{pid}")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public List<WholesalerProductDetailsDTO> getWholesalersByPid(@PathVariable String pid) throws ExecutionException, InterruptedException, IOException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        return wholesalerProductService.findByPid(pid, uid);
    }

    /**
     * get all the relevant products of a wholesaler
     * @return list of products for each wholesaler
     */
    @GetMapping("/wholesaler")
    @PreAuthorize("hasAnyRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ProductDetailedDTO> getProductsByUEN() throws ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        return wholesalerProductService.getByWholesalerUID(uid);
    }

    /**
     * Add a new product (for wholesalers only)
     * @return void
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code=HttpStatus.CREATED)
    public void addProduct(@RequestBody WholesalerProductDTO wholesalerProductDTO){
        wholesalerProductService.addWholesalerProduct(wholesalerProductDTO);
    }

    /**
     * Update a product (for wholesalers only)
     * @return void
     */
    @PatchMapping("/update/{swpid}")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable String swpid, @RequestBody Map<String, Object> updates) throws ExecutionException, InterruptedException {
        wholesalerProductService.updateWholesalerProduct(swpid,updates);// Call service to update the product's details
    }

    /**
     * Delete a wholesaler product by Pid
     * @return void
     */
    @DeleteMapping("/delete/{swpid}")
    @PreAuthorize(("hasRole('WHOLESALER')"))
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteWholesalerProduct(@PathVariable String swpid) throws ExecutionException, InterruptedException {
        wholesalerProductService.deleteWholesalerProduct(swpid); // Call service to delete the product by PID
    }

    /**
     * Returns the image of a product via its name.
     * @param name The name of the product.
     * @return ResponseEntity containing the image URL or an error message.
     */
    @GetMapping("/image")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> getProductImage(@RequestParam String name) throws ExecutionException, InterruptedException {
        String imageUrl = productService.getImageURLByProductName(name);
        if (imageUrl != null) {
            return ResponseEntity.ok(imageUrl);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No image available");
        }
    }
}
