package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.service.ProductService;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ProductDTO> getAllProducts() throws ExecutionException, InterruptedException {
        return productService.getAllProducts();
    }

    /**
     * get wholesalers by pid for consumers
     * @return List of wholesalers
     */
    @GetMapping("/{pid}")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public List<WholesalerProductDTO> getWholesalersByPid(@PathVariable String pid) throws ExecutionException, InterruptedException {
        return wholesalerProductService.findByPid(pid);
    }

    /**
     * get all the relevant products of a wholesaler
     * @return list of products for each wholesaler
     */
    @GetMapping("/wholesaler/{uen}")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public List<WholesalerProductDTO> getProductsByUEN(@PathVariable String uen) throws ExecutionException, InterruptedException {
        return wholesalerProductService.getByWholesalerUEN(uen);
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
    @PatchMapping("/delete/{swpid}")
    @PreAuthorize(("hasRole('WHOLESALER')"))
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteWholesalerProduct(@PathVariable String swpid) throws ExecutionException, InterruptedException {
        wholesalerProductService.deleteWholesalerProduct(swpid); // Call service to delete the product by PID
    }

}
