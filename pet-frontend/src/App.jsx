import './App.css'

import {createBrowserRouter, createRoutesFromElements, Route, RouterProvider} from "react-router-dom";
import RootLayout from "./components/layout/RootLayout.jsx";
import Home from "./components/home/Home.jsx";

function App() {
    const router = createBrowserRouter(
        createRoutesFromElements(
            <Route path='/' element={<RootLayout />}>
                <Route index element={<Home />} />
            </Route>
        )
    );
    return (
        <main className=''>
            <RouterProvider router={router} />
        </main>
    );
}

export default App
