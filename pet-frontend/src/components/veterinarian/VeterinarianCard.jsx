import { Accordion, Col, Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import UserImage from "../common/UserImage.jsx";
import placeholder from "../../assets/images/placeholder.jpg";

const VeterinarianCard = ({ vet }) => {
    return (
        <Col key={vet.id} className='mb-4 xs={12}'>
            <Accordion>
                <Accordion.Item eventKey='0'>
                    <Accordion.Header>
                        <div className='d-flex align-items-center'>
                            <Link to={""}>
                                <UserImage
                                    userId={vet.id}
                                    userPhoto={vet.photo}
                                    placeholder={placeholder}
                                />
                            </Link>
                        </div>
                        <div className='flex-grow-1 ml-3 px-5'>
                            <Card.Title className='title'>
                                Dr.{vet.firstName} {vet.lastName}
                            </Card.Title>
                            <Card.Title>
                                <h6>{vet.specialization}</h6>
                            </Card.Title>
                            <Card.Text className='review rating-stars'>
                                Reviews: stars
                            </Card.Text>
                            <Link to={""} className="link">
                                Calendario de Turnos
                            </Link>
                        </div>
                    </Accordion.Header>
                    <Accordion.Body>
                        <div>
                            <Link to={""} className='link-2'>
                                Mira opiniones de pacientes sobre
                            </Link>{" "}
                            <span className='margin-left-space'>Dr.{vet.firstName}</span>
                        </div>
                    </Accordion.Body>
                </Accordion.Item>
            </Accordion>
        </Col>
    );
};

export default VeterinarianCard;