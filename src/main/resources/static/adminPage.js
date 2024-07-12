import {
  addProduct,
  deleteProduct,
  editProduct,
  pagination
} from './productAPI.js';

const modal = document.getElementById('productModal');
const closeButton = document.getElementsByClassName('close')[0];
const mainCheckbox = document.querySelector('table th input[type="checkbox"]');
const checkboxes = document.querySelectorAll('table td input[type="checkbox"]');

export function addProductbtnOnClick() {
  modal.getElementsByTagName('h1')[0].innerText = 'Add a new product';
  modal.getElementsByTagName('button')[0].onclick = addProduct.bind(
      null,
  );
  modal.style.display = 'flex';
  document.getElementById('product-name-error-message').style.display =
      'none';
}

window.addProductbtnOnClick = addProductbtnOnClick;

export function editProductbtnOnClick(id) {
  modal.getElementsByTagName('h1')[0].innerText = 'Edit product';
  modal.getElementsByTagName('button')[0].onclick = editProduct.bind(
      null,
      id,
  );
  modal.style.display = 'flex';
  document.getElementById('product-name-error-message').style.display =
      'none';
}

window.editProductbtnOnClick = editProductbtnOnClick;

closeButton.onclick = function () {
  modal.modal.style.display = 'none';
};

window.onclick = function (event) {
  if (event.target == modal) {
    modal.style.display = 'none';
  }
};

export function mainCheckboxOnClick() {
  checkboxes.forEach((checkbox) => {
    checkbox.checked = mainCheckbox.checked;
  });
}

window.mainCheckboxOnClick = mainCheckboxOnClick;

export function checkboxOnClick() {
  if (
      document.querySelectorAll('table td input[type="checkbox"]:checked')
          .length === checkboxes.length
  ) {
    mainCheckbox.checked = true;
  } else {
    mainCheckbox.checked = false;
  }
}

window.checkboxOnClick = checkboxOnClick;

export function deleteCheckedProductsOnClick() {
  const selectedCheckboxes = document.querySelectorAll(
      'table td input[type="checkbox"]:checked'
  );

  selectedCheckboxes.forEach((checkbox) => {
    const id = checkbox
    .closest('tr')
    .querySelector('td:nth-child(2)').innerText; // ID is in the second column
    deleteProduct(id);
  });
  mainCheckbox.checked = false;
}

window.deleteCheckedProductsOnClick = deleteCheckedProductsOnClick;

export function pageSizeSelected() {
  const pageSizeSelector = document.getElementById("page-size");
  pagination(1, pageSizeSelector.value, "")
}

window.pageSizeSelected = pageSizeSelected