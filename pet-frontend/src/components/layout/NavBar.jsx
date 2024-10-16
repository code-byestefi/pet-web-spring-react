import { Navbar, Container, Nav, NavDropdown } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import logo from "../../assets/images/logo.png";

const NavBar = () => {
    return (
        <Navbar expand='lg' sticky='top' className='nav-bg'>
            <Container>
                <Navbar.Brand to={"/"} as={Link} className='nav-home'>
                    <img src={logo} alt="Logo VidaAnimal" style={{ width: '70px', height: '70px' }}  />
                </Navbar.Brand>
                <Navbar.Toggle aria-controls='responsive-navbar-nav' />
                <Navbar.Collapse id='responsive-navbar-nav'>
                    <Nav className='me-auto'>
                        <Nav.Link to={"/doctors"} as={Link}>
                            Nuestros Veterinarios
                        </Nav.Link>
                        <Nav.Link to={"/admin-dashboard"} as={Link}>
                            Admin
                        </Nav.Link>
                    </Nav>
                    <Nav>
                        <NavDropdown title='Cuenta' id='basic-nav-dropdown'>
                            <NavDropdown.Item to={"/user-dashboard"} as={Link}>
                                Registrarse
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item to={"/user-dashboard"} as={Link}>
                                Iniciar Sesión
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item to={"/user-dashboard"} as={Link}>
                                Mi perfil
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item to={"/admin-dashboard"} as={Link}>
                                Perfil Admin
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item to={"/logout"} as={Link}>
                                Cerrar Sesión
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default NavBar;