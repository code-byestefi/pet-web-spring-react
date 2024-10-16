import React, { useState } from "react";
import UseMessageAlerts from "../../hooks/UseMessageAlerts.js";
import { Form, Row, Col, Button } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format } from "date-fns";
import AlertMessage from "../common/AlertMessage";
import { findAvailableVeterinarians } from "./VeterinarianService";

const VeterinarianSearch = ({ onSearchResult }) => {
    const [searchQuery, setSearchQuery] = useState({
        date: null,
        time: null,
        specialization: "",
    });
    const [showDateTime, setShowDateTime] = useState(false);
    const { errorMessage, setErrorMessage, showErrorAlert, setShowErrorAlert } =
        UseMessageAlerts();

    const handleInputchange = (e) => {
        setSearchQuery({ ...searchQuery, [e.target.name]: e.target.value });
    };

    const handleDateChange = (date) => {
        setSearchQuery({ ...searchQuery, date });
    };
    const handleTimeChange = (time) => {
        setSearchQuery({ ...searchQuery, time });
    };

    const handleDateTimeToggle = (e) => {
        const isChecked = e.target.checked;
        setShowDateTime(isChecked);
        if (isChecked) {
            setSearchQuery({ ...searchQuery, date: null, time: null });
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        let searchParams = { specialization: searchQuery.specialization };

        if (searchQuery.date) {
            const formattedDate = format(searchQuery.date, "yyyy-MM-dd");
            searchParams.date = formattedDate;
        }
        if (searchQuery.time) {
            const formattedTime = format(searchQuery.time, "HH:mm");
            searchParams.time = formattedTime;
        }
        try {
            const response = await findAvailableVeterinarians(searchParams);
            onSearchResult(response.data);
            setShowErrorAlert(false);
        } catch (error) {
            setErrorMessage(error.response.data.message);
            setShowErrorAlert(true);
        }
    };

    const handleClearSearch = () => {
        setSearchQuery({
            date: null,
            time: null,
            specialization: "",
        });
        setShowDateTime(false);
        onSearchResult(null);
    };

    return (
        <section className='stickyFormContainer'>
            <h3>Encontra tu veterinario</h3>
            <Form onSubmit={handleSearch}>
                <Form.Group>
                    <Form.Label>Especialidad</Form.Label>
                    <Form.Control
                        as='select'
                        name='specialization'
                        value={searchQuery.specialization}
                        onChange={handleInputchange}>
                        <option value=''>Seleccionar especialidad</option>
                        <option value='Surgeon'>Cirujano</option>
                        <option value='Urologist'>Urologia</option>
                        <option value='Other'>Otros</option>
                    </Form.Control>
                </Form.Group>

                <fieldset>
                    <Row className='mb-3'>
                        <Col>
                            <Form.Group className='mb-3 mt-3'>
                                <Form.Check
                                    type='checkbox'
                                    label='Incluir fecha y tiempo disponible'
                                    checked={showDateTime}
                                    onChange={handleDateTimeToggle}
                                />
                            </Form.Group>
                            {showDateTime && (
                                <React.Fragment>
                                    <legend>Ingresar fecha y hora</legend>
                                    <Form.Group className='mb-3'>
                                        <Form.Label className='searchText'>Fecha</Form.Label>
                                        <DatePicker
                                            selected={searchQuery.date}
                                            onChange={handleDateChange}
                                            dateFormat='yyyy-MM-dd'
                                            minDate={new Date()}
                                            className='form-control'
                                            placeholderText='Select date'
                                        />
                                    </Form.Group>
                                    <Form.Group className='mb-3'>
                                        <Form.Label className='searchText'>Hora</Form.Label>
                                        <DatePicker
                                            selected={searchQuery.time}
                                            onChange={handleTimeChange}
                                            showTimeSelect
                                            showTimeSelectOnly
                                            timeIntervals={30}
                                            dateFormat='HH:mm'
                                            className='form-control'
                                            placeholderText='Select time'
                                            required
                                        />
                                    </Form.Group>
                                </React.Fragment>
                            )}
                        </Col>
                    </Row>
                </fieldset>

                <div className='d-flex justify-content-center mb-4'>
                    <Button type='submit' variant='outline-primary'>
                        Buscar
                    </Button>
                    <div className='mx-2'>
                        <Button
                            type='button'
                            variant='outline-info'
                            onClick={handleClearSearch}>
                            Limpiar búsqueda
                        </Button>
                    </div>
                </div>
            </Form>
            <div>
                {showErrorAlert && (
                    <AlertMessage type={"danger"} message={errorMessage} />
                )}
            </div>
        </section>
    );
};

export default VeterinarianSearch;
