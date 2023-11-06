import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: 'product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {


  products: Product[] = [];
  currentCategoryId: number = 1;
  previousCategoryId: number = 1;
  searchMode: boolean = false;



  // new properties for pagination
  thePageNumber: number = 1;
  thePageSize: number = 12;
  theTotalElements: number = 0;


  previousKeyword: string = "";


  constructor(private productService: ProductService, private route: ActivatedRoute, private cartService: CartService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
  }



  listProducts() {

    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if (this.searchMode) {

      this.handleSearchProdcuts();
    } else {

      this.handleListProduct();
    }
  }




  handleListProduct() {
    // check if id parameter is available
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id')

    if (hasCategoryId) {
      // get the id param string. convert string to a number using + symbol
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
    } else {
      // not category id available ... default to category id 1
      this.currentCategoryId = 1;
    }

    // Check if we have different category than previous
    // Note: Angular will reuse a component if it is currently being viewed

    // if we have a different category id than previos then set thePageNumber back to 1
    if (this.previousCategoryId != this.currentCategoryId) {
      this.thePageNumber = 1;
    }

    this.previousCategoryId = this.currentCategoryId;

    console.log(this.thePageNumber, this.thePageSize, this.theTotalElements);

    // now get the products for the given category id
    this.productService.getProductListPaginate(this.currentCategoryId, this.thePageNumber, this.thePageSize).subscribe(this.procesResult())

  };



  handleSearchProdcuts() {

    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;

    // if we have a different keyword than previous than set thePageNumber to 1
    if (this.previousKeyword != theKeyword) {
      this.thePageNumber = 1;
    }

    this.previousKeyword = theKeyword;

    console.log(`keyword=${theKeyword}, thePageNumber=${this.thePageNumber}`);

    // now search for products using keyword
    this.productService.searchProductsPaginate(theKeyword, this.thePageNumber, this.thePageSize).subscribe(
      this.procesResult())

  };




  updatePageSize(pageSize: string) {

    this.thePageSize = +pageSize;
    this.thePageNumber = 1;
    this.listProducts();

  }

  procesResult() {

    return (data: any) => {

      this.products = data.data
      this.thePageNumber = data.page
      this.thePageSize = data.totalPages
      this.theTotalElements = data.totalElements


    };
  }

  addToCart(theProduct: Product){

    console.log(`Adding to cart: ${theProduct.name}, ${theProduct.unitPrice}`);

    const theCartItem = new CartItem(theProduct);

    this.cartService.addToCart(theCartItem);

  }
}
