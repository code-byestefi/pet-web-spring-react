import React, {useState} from "react";
import {Form} from "react-bootstrap";
import AddItemModal from "../components/modals/AddItemModal.jsx";


const PetColorSelector = ({value, onChange}) => {

    const [petColors, setPetColors] = useState([]);
    const [showModal, setshowModal] = useState(false);


    // handle para cambiar el color
    const handleColorChange = (e) => {
        if (e.target.value === "add-new-item") {
            setshowModal(true);
        } else {
            onChange(e);
        }
    };

    // handle para guardar el nuevo item
    const handleSaveNewItem = (newItem) => {
        if (newItem && !petColors.includes(newItem)) {
            setPetColors([...petColors, newItem]);
            onChange({ target : { name: "petColor", value: newItem}});
        }
    };

    return (
        <React.Fragment>
            <Form.Group>
                <Form.Control
                    as="select"
                    name="petColor"
                    value={value}
                    required
                    onChange={handleColorChange}
                >
                    <option>seleccionar color</option>
                    <option value='add-new-item'>agregar nuevo color</option>
                    <option value='white'>white</option>

                </Form.Control>
            </Form.Group>
            <AddItemModal
                show={showModal}
                handleClose={() => setshowModal(false)}
                itemLabel={'Color'}
                handleSave={handleSaveNewItem}
            />
        </React.Fragment>
    );

}

export default PetColorSelector;