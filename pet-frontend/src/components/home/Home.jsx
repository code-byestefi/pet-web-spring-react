// eslint-disable-next-line no-unused-vars
import React from 'react';
import {Button, Card, Col, Container, ListGroup, Row} from "react-bootstrap";
import d5 from "../../assets/images/op11.jpg";
import vett from "../../assets/images/op12.jpg";

const Home = () => {
    return (
        <Container className="home-container mt-5">
            <Row>
                <Col md={6} className="mb-3">
                    <Card>
                        <Card.Img
                            variant='top'
                            src={d5}
                            alt='Sobre nosotros'
                            className='hero-image'
                            style={{ width: '100%' }}
                        />
                        <Card.Body>
                            <h2 className='text-info'>Quienes somos...</h2>
                            <Card.Title>Atención integral para tus amigos peludos</Card.Title>
                            <Card.Text>
                                En Universal Pet Care, creemos que cada mascota merece lo mejor.
                                Nuestro equipo de profesionales dedicados está aquí para garantizar la
                                salud y felicidad de su mascota a través de servicios veterinarios integrales.
                                Con décadas de experiencia combinada, nuestros veterinarios y
                                personal de apoyo están comprometidos a brindar atención personalizada
                                adaptada a las necesidades únicas de cada mascota.
                            </Card.Text>
                            <Card.Text>
                                Ofrecemos una amplia gama de servicios, desde atención preventiva y
                                chequeos de rutina hasta procedimientos quirúrgicos avanzados y atención de
                                emergencia. Nuestras instalaciones de última generación están equipadas con lo último
                                en tecnología veterinaria, lo que nos permite brindar atención de alta
                                calidad con precisión y compasión. Ofrecemos una amplia
                                gama de servicios, desde atención preventiva y chequeos de rutina hasta
                                procedimientos quirúrgicos avanzados y atención de emergencia. Nuestras instalaciones de
                                última generación están equipadas con lo último en
                                tecnología veterinaria, lo que nos permite brindar atención de alta
                                calidad con precisión y compasión. Nuestras instalaciones de última generación
                                están equipadas con lo último en tecnología veterinaria, lo que nos permite brindar
                                atención de alta calidad.
                            </Card.Text>
                            <Button variant='outline-info'>Conoce nuestros veterinarios</Button>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={6} className='mb-3'>
                    <Card className="service-card">
                        <Card.Img
                            variant='top'
                            src={vett}
                            alt='About Us'
                            className='hero-image'
                            style={{ width: '100%' }}
                        />
                        <Card.Body>
                            <h2 className='text-info'>Nuestros Servicios</h2>
                            <Card.Title>Que ofrecemos</Card.Title>
                            <ListGroup className='services-list'>
                                <ListGroup.Item>Revisiones</ListGroup.Item>
                                <ListGroup.Item>Cirugía de emergencia</ListGroup.Item>
                                <ListGroup.Item>Vacunación</ListGroup.Item>
                                <ListGroup.Item>Cuidado dental</ListGroup.Item>
                                <ListGroup.Item>Esterilización y castración</ListGroup.Item>
                                <ListGroup.Item>Y mucho más...</ListGroup.Item>
                            </ListGroup>
                            <Card.Text className='mt-3'>
                                Desde controles de rutina hasta cirugías de emergencia, nuestra gama completa de
                                servicios veterinarios garantiza que la salud de su mascota esté en buenas manos.
                            </Card.Text>
                            <Button variant='outline-info'> Conoce nuestros veterinarios</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <div className='card mb-5'>
                <h4>
                    Opiniones de pacientes de {" "}
                    <span className='text-info'>Vida Animal</span>
                </h4>
                <hr />

                <p className="text-center">Aqui abajo estarán las reviews</p>
            </div>
        </Container>
    );
}

export default Home;