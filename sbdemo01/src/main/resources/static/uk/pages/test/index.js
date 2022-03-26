export default function TestPage({data}){
    return (
        <div>
            {
                data.dataseries.map((item)=>{
                    return (
                        <div>
                            {item.seeing}
                        </div>
                    )
                })
            }
        </div>
    )
}

export async function getServerSideProps() {

    const res = await fetch(`https://www.7timer.info/bin/astro.php?lon=113.2&lat=23.1&ac=0&unit=metric&output=json&tzshift=0`)
    const data = await res.json()

    return {
      props: { data }
    }
}
